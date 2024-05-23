using FitHub.Data;

namespace FitHub.Web.Modeles.WorkoutModels
{
    public class ExerciseWithNames 
    {
        public int Exerciseinfoid { get; set; }

        public int? Exerciseid { get; set; }
        public int? Planid { get; set; }
        public int? Place { get; set; }

        public int? Sets { get; set; }

        public int? Reps { get; set; }

        public double? weightload { get; set; }

        public TimeSpan? leadtime { get; set; }
        public string name { get; set; }
    }
}
