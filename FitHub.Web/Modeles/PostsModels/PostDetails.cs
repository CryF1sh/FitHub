﻿namespace FitHub.Web.Modeles
{
    public class PostDetails
    {
        public int Postid { get; set; }
        public string Title { get; set; }
        public string CreatorFirstName { get; set; }
        public string CreatorLastName { get; set; }
        public int? CreatorProfilePictureId { get; set; }
        public DateTime? CreationDate { get; set; }
        public string Content { get; set; }
    }
}
