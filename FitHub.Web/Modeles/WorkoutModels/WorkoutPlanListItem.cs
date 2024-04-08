namespace FitHub.Web.Modeles.WorkoutModels
{
    public class WorkoutPlanListItem
    {
        public int Planid { get; set; }

        public DateTime? Creationdate { get; set; }

        public string? Name { get; set; }
        public string Creatorid { get; set; }
    }
}
