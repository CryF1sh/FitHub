using System;
using System.Collections.Generic;

namespace FitHub.Data;

public partial class Exercise
{
    public int Exerciseid { get; set; }

    public string? Name { get; set; }

    public virtual ICollection<Exerciseinfo> Exerciseinfos { get; set; } = new List<Exerciseinfo>();
}
