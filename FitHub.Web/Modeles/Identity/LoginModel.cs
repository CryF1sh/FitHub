using System.ComponentModel.DataAnnotations;

namespace FitHub.Web.Modeles.Identity
{
    public class LoginModel
    {
        [Required(ErrorMessage = "Поле Email обязательно для заполнения")]
        [EmailAddress(ErrorMessage = "Неверный формат Email")]
        public string Email { get; set; }

        [Required(ErrorMessage = "Поле Пароль обязательно для заполнения")]
        [DataType(DataType.Password)]
        public string Password { get; set; }
    }
}
