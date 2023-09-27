using System;
using System.Collections.Generic;

namespace FitHub.Data;

public partial class Status
{
    public int Statusid { get; set; }

    public string? Name { get; set; }

    public virtual ICollection<Comment> Comments { get; set; } = new List<Comment>();

    public virtual ICollection<Post> Posts { get; set; } = new List<Post>();
}
