using System;
using System.Collections.Generic;

namespace FitHub.Data;

public partial class Chatparticipant
{
    public int Participantid { get; set; }

    public int Chatid { get; set; }

    public string Userid { get; set; }

    public bool Ismoderator { get; set; }

    public virtual Chat? Chat { get; set; }

    public virtual ApplicationUser? User { get; set; }
}
