using System;
using System.Collections.Generic;

namespace FitHub.Data;

public partial class Postssport
{
    public int Postsportid { get; set; }

    public int? Postid { get; set; }

    public int? Sporttypeid { get; set; }

    public virtual Post? Post { get; set; }

    public virtual Sporttype? Sporttype { get; set; }
}
