namespace FitHub.Web.Interfaces
{
    public interface ISendEmail
    {
        Task SendEmailAsync(string email, string subject, string message);
    }
}
