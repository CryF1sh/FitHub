using System.ComponentModel.DataAnnotations;

namespace FitHub.Web.Modeles.Identity
{
    public class ForgotPasswordModel
    {
        [Required(ErrorMessage = "Поле адреса электронной почты обязательно.")]
        [EmailAddress(ErrorMessage = "Введите корректный адрес электронной почты.")]
        [Display(Name = "Email")]
        public string Email { get; set; }
    }
}
