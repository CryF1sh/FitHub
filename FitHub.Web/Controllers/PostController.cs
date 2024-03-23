using FitHub.Data;
using FitHub.Web.Data;
using FitHub.Web.Interfaces;
using FitHub.Web.Modeles;
using FitHub.Web.Modeles.PostsModels;
using Markdig;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using System.Security.Claims;

namespace FitHub.Web.Controllers
{
    [Route("api/posts")]
    [ApiController]
    [Authorize] 
    public class PostController : ControllerBase
    {
        private readonly ApplicationDbContext _context;
        private readonly UserManager<ApplicationUser> _userManager;
        private readonly IConfiguration Configuration;
        private readonly ISendEmail _emailSender;

        public PostController(ApplicationDbContext context, UserManager<ApplicationUser> userManager, ISendEmail emailSender, IConfiguration configuration)
        {
            _context = context;
            _userManager = userManager;
            _emailSender = emailSender;
            Configuration = configuration;
        }

        // GET: api/posts
        [HttpGet]
        [AllowAnonymous]
        [Route("posts")]
        public async Task<IActionResult> GetPosts([FromQuery] int page, [FromQuery] int pageSize)
        {
            var posts = await _context.Posts
                .Include(p => p.User)
                .OrderByDescending(p => p.Creationdate) 
                .Skip((page - 1) * pageSize)
                .Take(pageSize)
                //.Where(p => p.Statusid == 1)
                .Select(p => new PostListItem 
                {
                    PostId = p.Postid,
                    Title = p.Title,
                    CreatorFirstName = p.User.Firstname,
                    CreatorLastName = p.User.Lastname,
                    CreationDate = p.Creationdate.ToString(),
                    TitleImageId = p.Titleimageid,
                })
                .ToListAsync();
                
            return Ok(posts);
        }

        // GET: api/posts_list_1c
        [HttpGet]
        [AllowAnonymous]
        [Route("posts_list_1c")]
        public async Task<IActionResult> GetMinimalPostData()
        {
            var minimalData = await _context.Posts
                .Where(p => p.Statusid == 0)
                .Select(p => new PostDetails
                {
                    Postid = p.Postid,
                    Title = p.Title,
                    //Status = p.Statusid
                })
                .ToListAsync();

            return Ok(minimalData);
        }

        // GET: api/posts_content_to_HTML/{ididpost}
        [HttpGet("posts_content_to_HTML/{id}")]
        [AllowAnonymous]
        public async Task<IActionResult> GetPostFullData(int id)
        {
            var pipeline = new MarkdownPipelineBuilder().Build();
            var post = await _context.Posts
              .Where(p => p.Postid == id).Select(p => new PostDetails
              {
                  CreatorFirstName = p.User.Firstname,
                  CreatorLastName = p.User.Lastname,
                  CreatorProfilePictureId = p.User.Profilepictureid,
                  CreationDate = p.Creationdate.ToString(),
                  Content = Markdown.ToHtml(p.Content, pipeline, null),
                  Titleimageid = p.Titleimageid,
              })
              .FirstOrDefaultAsync();

            if (post == null)
            {
                return NotFound();
            }

            return Ok(post);
        }

        // GET: api/posts/{id}
        [HttpGet("{id}")]
        [AllowAnonymous]
        public async Task<IActionResult> GetPost(int id)
        {
            var post = await _context.Posts
                .Where(p => p.Postid == id)
                .Select(p => new PostDetails
                {
                    Postid = p.Postid,
                    Title = p.Title,
                    CreatorFirstName = p.User.Firstname,
                    CreatorLastName = p.User.Lastname,
                    CreatorProfilePictureId = p.User.Profilepictureid,
                    CreationDate = p.Creationdate.ToString(),
                    Content = p.Content
                })
                .FirstOrDefaultAsync();

            if (post == null)
            {
                return NotFound();
            }

            return Ok(post);
        }

        // POST: api/posts
        [HttpPost]
        public async Task<IActionResult> CreatePost([FromBody] PostCreate postС)
        {
            if (ModelState.IsValid)
            {
                var userId = User.FindFirst(ClaimTypes.NameIdentifier)?.Value;

                var post = new Post
                {
                    Userid = userId,
                    Title = postС.Title,
                    Content = postС.Content,
                    Titleimageid = postС.TitleImageId,
                    Creationdate = DateTime.Now,
                    Statusid = 0,
                    // Добавить сюда позже поля связанные с изоображениями
                };

                _context.Posts.Add(post);
                await _context.SaveChangesAsync();

                return CreatedAtAction(nameof(GetPost), new { id = post.Postid }, post);
            }

            return BadRequest(ModelState);
        }


        // PUT: api/posts/5
        [HttpPut("{id}")]
        public async Task<IActionResult> UpdatePost(int id, [FromBody] PostUpdate postUp)
        {
            if (id != postUp.Postid)
            {
                return BadRequest();
            }

            var existingPost = await _context.Posts.FindAsync(id);
            if (existingPost == null)
            {
                return NotFound();
            }

            // Текущий пользователь = создатель
            var userId = User.FindFirst(ClaimTypes.NameIdentifier)?.Value;
            if (existingPost.Userid != userId)
            {
                return Forbid();
            }

            existingPost.Title = postUp.Title;
            existingPost.Content = postUp.Content;
            // Добавить сюда позже поля связанные с изоображениями

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!PostExists(id))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return NoContent();
        }


        // DELETE: api/posts/5
        [HttpDelete("{id}")]
        public async Task<IActionResult> DeletePost(int id)
        {
            var post = await _context.Posts.FindAsync(id);
            if (post == null)
            {
                return NotFound();
            }

            // Текущий пользователь = создатель
            var userId = User.FindFirst(ClaimTypes.NameIdentifier)?.Value;
            if (post.Userid != userId)
            {
                return Forbid(); 
            }

            _context.Posts.Remove(post);
            await _context.SaveChangesAsync();

            return NoContent();

        }

        // PUT: api/posts/updatestatus/{id}
        [HttpPut("updatestatus/{id}")]
        [AllowAnonymous]
        public async Task<IActionResult> UpdatePostStatus(int id, [FromBody] PostStatusUpdate postStatusUpdate)
        {
            var existingPost = await _context.Posts.FindAsync(id);

            if (existingPost == null)
            {
                return NotFound();
            }

            var app1CSecretKey = Configuration["AppSettings:1CSecretKey"];

            var requestSecretKey = HttpContext.Request.Headers["SecretKey"].FirstOrDefault();

            if (requestSecretKey != app1CSecretKey)
            {
                return Forbid("Неправильный секретный ключ!");
            }

            existingPost.Statusid = postStatusUpdate.Statusid;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!PostExists(id))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            try
            {
                var userEmail = await _context.Posts
                    .Include(p => p.User)
                    .Where(p => p.Postid == id)
                    .Select(p => new
                    {
                        UserEmail = p.User.Email,
                    })
                    .FirstOrDefaultAsync();

                var postTitle = await _context.Posts
                    .Where(p => p.Postid == id)
                    .Select(p => new
                    {
                        PostTitle = p.Title,
                    })
                    .FirstOrDefaultAsync();

                if (userEmail != null)
                {
                    string email = userEmail.UserEmail;
                    string statusMessage = "";
                    if (postStatusUpdate.Statusid == 1) //статус 0 - на рассмотрении, 1 - одобрено, 2 - отклонено, 
                    {
                        statusMessage = $"Ваша статья {postTitle.PostTitle} была одобрена.";
                    }
                    else if (postStatusUpdate.Statusid == 2)
                    {
                        statusMessage = $"Ваша статья {postTitle.PostTitle} была отклонена. Причина: " + postStatusUpdate.Message;
                    }

                    await _emailSender.SendEmailAsync(
                           email,
                           "Изменение статуса статьи",
                           statusMessage);
                }
            }
            catch (DbUpdateConcurrencyException)
            {
                //if (!PostExists(postStatusUpdate.Postid))
                //{
                //    return NotFound();
                //}
                //else
                //{
                //    throw;
                //}
            }

            return NoContent();
        }


        private bool PostExists(int id)
        {
            return _context.Posts.Any(e => e.Postid == id);
        }
    }
}
