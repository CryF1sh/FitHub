namespace FitHub.Web.Modeles.Identity
{
    public class UpdateProfileModel
    {
        public string? Firstname { get; set; }
        public string? Lastname { get; set; }
        public string? Bio { get; set; }
        public string? Location { get; set; }
        public DateOnly? Birthdate { get; set; }
    }
}
