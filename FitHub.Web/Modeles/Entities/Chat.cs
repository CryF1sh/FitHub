using System;
using System.Collections.Generic;

namespace FitHub.Data;

public partial class Chat
{
    public int Chatid { get; set; }

    public int? Chattypeid { get; set; }

    public string? Name { get; set; }

    public virtual ICollection<Chatmessage> Chatmessages { get; set; } = new List<Chatmessage>();

    public virtual ICollection<Chatparticipant> Chatparticipants { get; set; } = new List<Chatparticipant>();

    public virtual Chattype? Chattype { get; set; }
}
