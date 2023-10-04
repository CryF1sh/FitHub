using System;
using System.Collections.Generic;

namespace FitHub.Data;

public partial class Sporttype
{
    public int Sporttypeid { get; set; }

    public string? Name { get; set; }

    public virtual ICollection<Postssport> Postssports { get; set; } = new List<Postssport>();

    public virtual ICollection<Usersport> Usersports { get; set; } = new List<Usersport>();
}
