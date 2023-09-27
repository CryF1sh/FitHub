using System;
using System.Collections.Generic;

namespace FitHub.Data;

public partial class Diary
{
    public int Diaryid { get; set; }

    public DateTime? Creationdate { get; set; }

    public string Creatorid { get; set; }

    public DateTime? Lastmodifieddate { get; set; }

    public virtual ApplicationUser? Creator { get; set; }

    public virtual ICollection<Diaryexercise> Diaryexercises { get; set; } = new List<Diaryexercise>();
}
