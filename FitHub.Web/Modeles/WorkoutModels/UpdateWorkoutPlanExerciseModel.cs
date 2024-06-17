namespace FitHub.Web.Modeles.WorkoutModels
{
    public class UpdateWorkoutPlanExerciseModel
    {
        public int? Exerciseinfoid { get; set; }
        public string? Name { get; set; }
        public int? Sets { get; set; }
        public int? Reps { get; set; }
        public double? Weightload { get; set; }
        public long? Leadtime { get; set; }
    }
}
