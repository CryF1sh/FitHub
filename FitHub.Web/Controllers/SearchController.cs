using FitHub.Data;
using FitHub.Web.Data;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;

namespace FitHub.Web.Controllers
{
    [Route("api/search")]
    [ApiController]
    public class SearchController : ControllerBase
    {

        private readonly ApplicationDbContext _context;

        public SearchController(ApplicationDbContext context)
        {
            _context = context;
        }
        // GET: api/search/exercises
        [HttpGet("exercises")]
        public async Task<ActionResult<IEnumerable<string>>> SearchExercisesByName([FromQuery] string name)
        {
            var exerciseNames = await _context.Exercises
                .Where(e => EF.Functions.ILike(e.Name, $"%{name}%"))
                .Select(e => e.Name)
                .ToListAsync();

            if (exerciseNames == null || !exerciseNames.Any())
            {
                return NotFound();
            }

            return exerciseNames;
        }
    }
}
