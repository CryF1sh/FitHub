using FitHub.Data;
using FitHub.Web.Data;
using FitHub.Web.Modeles;
using FitHub.Web.Modeles.WorkoutModels;
using Humanizer;
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
        public async Task<ActionResult<IEnumerable<ExerciseWithNames>>> GetWorkoutplanExercises(int id)
        {
            var workoutplan = await _context.Workoutplans.FindAsync(id);
            if (workoutplan == null)
            {
                return NotFound();
            }

            var exercisesWithNames = await _context.Exerciseinfos
                 .Where(e => e.Planid == id)
                 .OrderBy(pl => pl.Exerciseinfoid) //Заменить на place
                 .Join(_context.Exercises, ei => ei.Exerciseid, e => e.Exerciseid, (ei, e) => new ExerciseWithNames
                 {
                     Exerciseinfoid = ei.Exerciseinfoid,
                     Planid = ei.Planid,
                     Exerciseid = ei.Exerciseid,
                     Place = ei.Place,
                     Sets = ei.Sets, 
                     Reps = ei.Reps, 
                     weightload = ei.Weightload, 
                     leadtime = ei.Leadtime, 
                     name = e.Name
                 })
                 .ToListAsync();

            return exercisesWithNames;
        }


        // POST: api/workoutplan
        [HttpPost]
        public async Task<ActionResult<int>> CreateWorkoutplan([FromBody] WorkoutPlanCreate workoutplancreate)
        {
            var userId = User.FindFirst(ClaimTypes.NameIdentifier)?.Value;

            var workoutplan = new Workoutplan
            {
                Name = workoutplancreate.Name,
                Creationdate = DateTime.Now,
                Description = workoutplancreate.Description,
                Creatorid = userId, 
                Privacy = workoutplancreate.Privacy
            };

            _context.Workoutplans.Add(workoutplan);
            await _context.SaveChangesAsync();

            foreach (var exerciseInfo in workoutplancreate.Exercisesinfo)
            {
                //Найти каждое ID упражения по названию, если его нет - добавить новое упраждение в БД
                var exercise = await _context.Exercises.FirstOrDefaultAsync(e => e.Name == exerciseInfo.name);

                if (exercise == null)
                {
                    exercise = new Exercise { Name = exerciseInfo.name };
                    _context.Exercises.Add(exercise);
                    await _context.SaveChangesAsync();
                }

                var saveexerciseinfo = new Exerciseinfo
                {
                    Exerciseid = exercise.Exerciseid,
                    Planid = workoutplan.Planid,
                    Place = exerciseInfo.Place,
                    Sets = exerciseInfo.Sets,
                    Reps = exerciseInfo.Reps,
                    Weightload = exerciseInfo.Weightload,
                    Leadtime = exerciseInfo.Leadtime.HasValue ? TimeSpan.FromTicks(exerciseInfo.Leadtime.Value) : TimeSpan.Zero
                };

                _context.Exerciseinfos.Add(saveexerciseinfo);
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


            var existingExercises = await _context.Exerciseinfos
                .Where(e => e.Planid == id)
                .ToListAsync();

            // Обновляем или добавляем упражнения
            foreach (var exerciseModel in model.exercisesInfo)
            {
                var exercise = await _context.Exercises
                    .FirstOrDefaultAsync(e => e.Name == exerciseModel.Name);

                if (exercise == null)
                {
                    exercise = new Exercise { Name = exerciseModel.Name };
                    _context.Exercises.Add(exercise);
                    await _context.SaveChangesAsync();
                }

                var existingExerciseInfo = existingExercises
                    .FirstOrDefault(e => e.Exerciseid == exercise.Exerciseid);

                if (existingExerciseInfo != null)
                {
                    // Обновляем существующее упражнение
                    existingExerciseInfo.Sets = exerciseModel.Sets;
                    existingExerciseInfo.Reps = exerciseModel.Reps;
                    existingExerciseInfo.Weightload = exerciseModel.Weightload;
                    existingExerciseInfo.Leadtime = exerciseModel.Leadtime.HasValue ? TimeSpan.FromTicks(exerciseModel.Leadtime.Value) : TimeSpan.Zero;
                }
                else
                {
                    // Добавляем новое упражнение
                    var newExerciseInfo = new Exerciseinfo
                    {
                        Exerciseid = exercise.Exerciseid,
                        Planid = id,
                        Sets = exerciseModel.Sets,
                        Reps = exerciseModel.Reps,
                        Weightload = exerciseModel.Weightload,
                        Leadtime = exerciseModel.Leadtime.HasValue ? TimeSpan.FromTicks(exerciseModel.Leadtime.Value) : TimeSpan.Zero
                    };

                    _context.Exerciseinfos.Add(newExerciseInfo);
                }
            }

            // Удаляем упражнения, которых больше нет в обновленном плане
            var newExerciseIds = model.exercisesInfo
                .Select(e => _context.Exercises.FirstOrDefault(ex => ex.Name == e.Name).Exerciseid)
                .ToList();

            var exercisesToRemove = existingExercises
                .Where(e => !newExerciseIds.Contains(e.Exerciseid.Value))
                .ToList();

            _context.Exerciseinfos.RemoveRange(exercisesToRemove);

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

            return Ok(id);
        }

        private bool WorkoutPlanExists(int id)
        {
            return _context.Workoutplans.Any(wp => wp.Planid == id);
        }

    }
}
