using System;
using System.Collections.Generic;

namespace FitHub.Data;

public partial class Chatmessage
{
    public int Messageid { get; set; }

    public int Chatid { get; set; }

    public string Userid { get; set; }

    public string Content { get; set; }

    public DateTime Timestamp { get; set; }

    public virtual Chat? Chat { get; set; }

    public virtual ApplicationUser? User { get; set; }
}
