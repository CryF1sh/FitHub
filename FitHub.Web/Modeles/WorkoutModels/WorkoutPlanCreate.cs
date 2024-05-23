using FitHub.Data;

namespace FitHub.Web.Modeles.WorkoutModels
{
    public class WorkoutPlanCreate
    {
        public string? Name { get; set; }

        public string? Description { get; set; }


        public bool? Privacy { get; set; }

        public List<ExerciseCreateModel>? Exercisesinfo { get; set; } = new List<ExerciseCreateModel>();
    }
}
