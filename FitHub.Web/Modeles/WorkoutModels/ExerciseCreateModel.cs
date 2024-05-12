namespace FitHub.Web.Modeles.WorkoutModels
{
    public class ExerciseCreateModel
    {
        public int? Exerciseid { get; set; }
        public int? Planid { get; set; }
        public int? Place { get; set; }

        public int? Sets { get; set; }

        public int? Reps { get; set; }

        public double? Weightload { get; set; }

        public TimeSpan? Leadtime { get; set; }
    }
}
