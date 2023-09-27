using System;
using System.Collections.Generic;

namespace FitHub.Data;

public partial class Image
{
    public int Imageid { get; set; }

    public byte[]? Imagedata { get; set; }

    public string? Imagetype { get; set; }

    public string? Userid { get; set; }

    public int? Postid { get; set; }

    public DateTime? Creationdate { get; set; }

    public virtual Post? Post { get; set; }

    public virtual ICollection<Postimage> Postimages { get; set; } = new List<Postimage>();

    public virtual ICollection<Post> Posts { get; set; } = new List<Post>();

    public virtual ApplicationUser? User { get; set; }

    public virtual ICollection<ApplicationUser> Users { get; set; } = new List<ApplicationUser>();
}
