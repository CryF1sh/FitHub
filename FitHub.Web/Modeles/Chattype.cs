using System;
using System.Collections.Generic;

namespace FitHub.Data;

public partial class Chattype
{
    public int Chattypeid { get; set; }

    public string? Name { get; set; }

    public virtual ICollection<Chat> Chats { get; set; } = new List<Chat>();
}
