namespace FitHub.Web.Modeles.WorkoutModels
{
    public class UpdateWorkoutPlanModel
    {
        public string Name { get; set; }
        public string Description { get; set; }
        public bool Privacy { get; set; }
        public List<UpdateWorkoutPlanExerciseModel> Workoutplanexercises { get; set; }
    }
}
