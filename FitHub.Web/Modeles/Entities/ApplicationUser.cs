using Microsoft.AspNetCore.Identity;
using System;
using System.Collections.Generic;

namespace FitHub.Data;

public partial class ApplicationUser : IdentityUser
{
    public string? Firstname { get; set; }

    public string? Lastname { get; set; }

    public int? Profilepictureid { get; set; }

    public string? Bio { get; set; }

    public string? Location { get; set; }

    public DateTime? Registrationdate { get; set; }

    public DateOnly? Birthdate { get; set; }

    public virtual ICollection<Chatmessage> Chatmessages { get; set; } = new List<Chatmessage>();

    public virtual ICollection<Chatparticipant> Chatparticipants { get; set; } = new List<Chatparticipant>();

    public virtual ICollection<Comment> Comments { get; set; } = new List<Comment>();

    public virtual ICollection<Diary> Diaries { get; set; } = new List<Diary>();

    public virtual ICollection<Image> Images { get; set; } = new List<Image>();

    public virtual ICollection<Post> Posts { get; set; } = new List<Post>();

    public virtual Image? Profilepicture { get; set; }

    public virtual ICollection<Usersport> Usersports { get; set; } = new List<Usersport>();

    public virtual ICollection<Workoutplan> Workoutplans { get; set; } = new List<Workoutplan>();
}
