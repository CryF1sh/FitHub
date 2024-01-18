using FitHub.Web.Interfaces;
using Microsoft.Extensions.Options;
using System.Net.Mail;
using System.Net;
using FitHub.Web.Modeles.Identity;
using Microsoft.CodeAnalysis;

namespace FitHub.Web.Services
{
    public class SendEmail : ISendEmail
    {
        private readonly EmailSettings _emailSettings;
        private readonly ILogger<SendEmail> _logger;

        public SendEmail(IOptions<EmailSettings> emailSettings, ILogger<SendEmail> logger)
        {
            _emailSettings = emailSettings.Value;
            _logger = logger;
        }

        public async Task SendEmailAsync(string email, string subject, string message)
        {
            try
            {
                var smtpClient = new SmtpClient
                {
                    Host = _emailSettings.SmtpServer,
                    Port = _emailSettings.SmtpPort,
                    EnableSsl = true,
                    Credentials = new NetworkCredential(_emailSettings.SmtpUsername, _emailSettings.SmtpPassword)
                };

                var mailMessage = new MailMessage
                {
                    From = new MailAddress(_emailSettings.SenderEmail, _emailSettings.SenderName),
                    Subject = subject,
                    Body = message,
                    //IsBodyHtml = true
                    IsBodyHtml = false
                };

                mailMessage.To.Add(email);

                await smtpClient.SendMailAsync(mailMessage);
                _logger.LogInformation("Email успешно отправлен.");
            }
            catch (Exception ex)
            {
                _logger.LogError(ex, "Ошибка при отправке email.");
                throw ex;
            }
        }
    }
}
