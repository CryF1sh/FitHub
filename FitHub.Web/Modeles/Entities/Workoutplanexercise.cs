using System;
using System.Collections.Generic;

namespace FitHub.Data;

public partial class Workoutplanexercise
{
    public int Workoutplanexerciseid { get; set; }

    public int? Planid { get; set; }

    public int? Exerciseinfoid { get; set; }

    public virtual Exerciseinfo? Exerciseinfo { get; set; }

    public virtual Workoutplan? Plan { get; set; }
}
