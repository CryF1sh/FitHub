using System;
using System.Collections.Generic;

namespace FitHub.Data;

public partial class Diaryexercise
{
    public int Diaryexerciseid { get; set; }

    public int? Diaryid { get; set; }

    public int? Exerciseinfoid { get; set; }

    public virtual Diary? Diary { get; set; }

    public virtual Exerciseinfo? Exerciseinfo { get; set; }
}
