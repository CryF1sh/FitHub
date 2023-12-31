﻿using System;
using System.Collections.Generic;

namespace FitHub.Data;

public partial class Workoutplan
{
    public int Planid { get; set; }

    public DateTime? Creationdate { get; set; }

    public string? Name { get; set; }

    public string? Description { get; set; }

    public string Creatorid { get; set; }

    public bool? Privacy { get; set; }

    public virtual ApplicationUser? Creator { get; set; }

    public virtual ICollection<Workoutplanexercise> Workoutplanexercises { get; set; } = new List<Workoutplanexercise>();
}
