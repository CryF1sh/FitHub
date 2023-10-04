using System;
using System.Collections.Generic;

namespace FitHub.Data;

public partial class Usersport
{
    public int Usersportid { get; set; }

    public string Userid { get; set; }

    public int Sporttypeid { get; set; }

    public virtual Sporttype? Sporttype { get; set; }

    public virtual ApplicationUser? User { get; set; }
}
