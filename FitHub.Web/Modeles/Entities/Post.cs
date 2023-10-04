using System;
using System.Collections.Generic;

namespace FitHub.Data;

public partial class Post
{
    public int Postid { get; set; }

    public string Userid { get; set; }

    public string? Title { get; set; }

    public int? Titleimageid { get; set; }

    public string? Content { get; set; }

    public DateTime? Creationdate { get; set; }

    public int? Statusid { get; set; }

    public virtual ICollection<Comment> Comments { get; set; } = new List<Comment>();

    public virtual ICollection<Image> Images { get; set; } = new List<Image>();

    public virtual ICollection<Postimage> Postimages { get; set; } = new List<Postimage>();

    public virtual ICollection<Postssport> Postssports { get; set; } = new List<Postssport>();

    public virtual Status? Status { get; set; }

    public virtual Image? Titleimage { get; set; }

    public virtual ApplicationUser? User { get; set; }
}
