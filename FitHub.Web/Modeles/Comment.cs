using System;
using System.Collections.Generic;

namespace FitHub.Data;

public partial class Comment
{
    public int Commentid { get; set; }

    public int Postid { get; set; }

    public string Userid { get; set; }

    public string? Content { get; set; }

    public DateTime? Creationdate { get; set; }

    public int? Statusid { get; set; }

    public virtual Post? Post { get; set; }

    public virtual Status? Status { get; set; }

    public virtual ApplicationUser? User { get; set; }
}
