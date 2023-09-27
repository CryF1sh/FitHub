using System;
using System.Collections.Generic;

namespace FitHub.Data;

public partial class Exerciseinfo
{
    public int Exerciseinfoid { get; set; }

    public int? Exerciseid { get; set; }

    public int? Sets { get; set; }

    public int? Reps { get; set; }

    public double? Weightload { get; set; }

    public TimeSpan? Leadtime { get; set; }

    public virtual ICollection<Diaryexercise> Diaryexercises { get; set; } = new List<Diaryexercise>();

    public virtual Exercise? Exercise { get; set; }

    public virtual ICollection<Workoutplanexercise> Workoutplanexercises { get; set; } = new List<Workoutplanexercise>();
}
