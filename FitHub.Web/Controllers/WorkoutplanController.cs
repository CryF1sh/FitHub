using FitHub.Data;
using FitHub.Web.Data;
using FitHub.Web.Modeles;
using FitHub.Web.Modeles.WorkoutModels;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Hosting;
using System.Security.Claims;

namespace FitHub.Web.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class WorkoutplanController : ControllerBase
    {
        private readonly ApplicationDbContext _context;

        public WorkoutplanController(ApplicationDbContext context)
        {
            _context = context;
        }

        // GET: api/workoutplan
        [HttpGet]
        public async Task<ActionResult<IEnumerable<Workoutplan>>> GetWorkoutplans()
        {
            return await _context.Workoutplans
                .Include(p => p.Creator)
                .OrderByDescending(p => p.Creationdate)
                .ToListAsync();
        }

        // GET: api/workoutplan/5
        [HttpGet("{id}")]
        public async Task<ActionResult<Workoutplan>> GetWorkoutplan(int id)
        {
            var workoutplan = await _context.Workoutplans.FindAsync(id);

            if (workoutplan == null)
            {
                return NotFound();
            }

            return workoutplan;
        }

        // GET: api/workoutplan/{id}/exercises
        [HttpGet("{id}/exercises")]
        public async Task<ActionResult<IEnumerable<Exerciseinfo>>> GetWorkoutplanExercises(int id)
        {
            var workoutplan = await _context.Workoutplans.FindAsync(id);
            if (workoutplan == null)
            {
                return NotFound();
            }

            var exercises = await _context.Exerciseinfos
                .Where(e => e.Planid == id)
                .OrderBy(pl => pl.Place)
                .ToListAsync();

            return exercises;
        }


        // POST: api/workoutplan
        [HttpPost]
        public async Task<ActionResult<int>> CreateWorkoutplan(WorkoutPlanCreate workoutplancreate)
        {
            var userId = User.FindFirst(ClaimTypes.NameIdentifier)?.Value;

            var workoutplan = new Workoutplan
            {
                Name = workoutplancreate.Name,
                Description = workoutplancreate.Description,
                Creatorid = userId, 
                Privacy = workoutplancreate.Privacy
            };

            _context.Workoutplans.Add(workoutplan);

            foreach (var exerciseInfo in workoutplancreate.Exercisesinfo)
            {
                var exerciseinfo = new Exerciseinfo
                {
                    Exerciseid = exerciseInfo.Exerciseid,
                    Planid = workoutplan.Planid,
                    Place = exerciseInfo.Place,
                    Sets = exerciseInfo.Sets,
                    Reps = exerciseInfo.Reps,
                    Weightload = exerciseInfo.Weightload,
                    Leadtime = exerciseInfo.Leadtime
                };

                _context.Exerciseinfos.Add(exerciseinfo);
            }


            await _context.SaveChangesAsync();

            return Ok(workoutplan.Planid);
        }

        // PUT: api/workoutplan/5
        [HttpPut("{id}")]
        public async Task<IActionResult> UpdateWorkoutPlan(int id, [FromBody] UpdateWorkoutPlanModel model)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            var workoutPlan = await _context.Workoutplans
                .Include(wp => wp.Workoutplanexercises)
                .ThenInclude(wpe => wpe.Exerciseinfo)
                .SingleOrDefaultAsync(wp => wp.Planid == id);

            if (workoutPlan == null)
            {
                return NotFound();
            }

            var userId = User.FindFirst(ClaimTypes.NameIdentifier)?.Value;
            if (workoutPlan.Creatorid != userId)
            {
                return Forbid();
            }

            workoutPlan.Name = model.Name;
            workoutPlan.Description = model.Description;
            workoutPlan.Privacy = model.Privacy;

            foreach (var exerciseModel in model.Workoutplanexercises)
            {
                var existingExercise = workoutPlan.Workoutplanexercises
                    .FirstOrDefault(wpe => wpe.Exerciseinfoid == exerciseModel.Exerciseinfoid);

                if (existingExercise != null)
                {
                    existingExercise.Exerciseinfo.Sets = exerciseModel.Sets;
                    existingExercise.Exerciseinfo.Reps = exerciseModel.Reps;
                    existingExercise.Exerciseinfo.Weightload = exerciseModel.Weightload;
                    existingExercise.Exerciseinfo.Leadtime = exerciseModel.Leadtime;
                    //Обновить place упраженения
                }
                else
                {
                    var newExerciseInfo = new Exerciseinfo
                    {
                        Exerciseinfoid = exerciseModel.Exerciseinfoid,
                        Sets = exerciseModel.Sets,
                        Reps = exerciseModel.Reps,
                        Weightload = exerciseModel.Weightload,
                        Leadtime = exerciseModel.Leadtime
                    };

                    var newWorkoutPlanExercise = new Workoutplanexercise
                    {
                        Planid = workoutPlan.Planid,
                        Exerciseinfoid = exerciseModel.Exerciseinfoid,
                        Exerciseinfo = newExerciseInfo
                    };

                    workoutPlan.Workoutplanexercises.Add(newWorkoutPlanExercise);
                }

            }

            var exercisesToRemove = workoutPlan.Workoutplanexercises
                .Where(wpe => !model.Workoutplanexercises.Any(em => em.Exerciseinfoid == wpe.Exerciseinfo.Exerciseinfoid))
                .ToList();

            foreach (var exerciseToRemove in exercisesToRemove)
            {
                _context.Workoutplanexercises.Remove(exerciseToRemove);
            }

            _context.Entry(workoutPlan).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!WorkoutPlanExists(id))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return Ok(workoutPlan.Planid);
        }

        private bool WorkoutPlanExists(int id)
        {
            return _context.Workoutplans.Any(wp => wp.Planid == id);
        }

    }
}
