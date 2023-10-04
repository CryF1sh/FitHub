using System;
using System.Collections.Generic;

namespace FitHub.Data;

public partial class Postimage
{
    public int Postimageid { get; set; }

    public int? Postid { get; set; }

    public int? Imageid { get; set; }

    public string? Imagelocation { get; set; }

    public virtual Image? Image { get; set; }

    public virtual Post? Post { get; set; }
}
