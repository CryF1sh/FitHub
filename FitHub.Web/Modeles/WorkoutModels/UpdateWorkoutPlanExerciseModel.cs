namespace FitHub.Web.Modeles.WorkoutModels
{
    public class UpdateWorkoutPlanExerciseModel
    {
        public int Exerciseinfoid { get; set; }
        public int Sets { get; set; }
        public int Reps { get; set; }
        public double Weightload { get; set; }
        public TimeSpan Leadtime { get; set; }
    }
}
