using FitHub.Data;
using FitHub.Web.Data;
using FitHub.Web.Modeles;
using FitHub.Web.Modeles.PostsModels;
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

        public PostController(ApplicationDbContext context, UserManager<ApplicationUser> userManager)
        {
            _context = context;
            _userManager = userManager;
        }

        // GET: api/posts
        [HttpGet]
        [AllowAnonymous]
        public async Task<IActionResult> GetPosts()
        {
            var posts = await _context.Posts
                .Include(p => p.User)
                .Select(p => new PostListItem 
                {
                    PostId = p.Postid,
                    Title = p.Title,
                    CreatorFirstName = p.User.Firstname,
                    CreatorLastName = p.User.Lastname,
                    CreationDate = p.Creationdate.ToString(),

                })
                .ToListAsync();

            return Ok(posts);
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
                    Creationdate = DateTime.Now
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

        private bool PostExists(int id)
        {
            return _context.Posts.Any(e => e.Postid == id);
        }
    }
}
