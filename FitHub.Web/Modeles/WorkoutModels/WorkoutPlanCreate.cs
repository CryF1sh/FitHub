﻿namespace FitHub.Web.Modeles.WorkoutModels
{
    public class WorkoutPlanCreate
    {
        public int Planid { get; set; }

        public DateTime? Creationdate { get; set; }

        public string? Name { get; set; }

        public string? Description { get; set; }

        public string Creatorid { get; set; }

        public bool? Privacy { get; set; }
    }
}
