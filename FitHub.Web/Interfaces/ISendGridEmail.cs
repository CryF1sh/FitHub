namespace FitHub.Web.Interfaces
{
    public interface ISendGridEmail
    {
        Task SendEmailAsync(string email, string subject, string message);
    }
}
