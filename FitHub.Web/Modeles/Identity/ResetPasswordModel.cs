using FitHub.Data;
using Microsoft.AspNetCore.Mvc;
using System.ComponentModel.DataAnnotations;

namespace FitHub.Web.Modeles.Identity
{
    public class ResetPasswordModel
    {
        [Required(ErrorMessage = "Поле нового пароля обязательно.")]
        [StringLength(100, ErrorMessage = "Поле {0} должно быть не менее {2} символов в длину.", MinimumLength = 6)]
        [DataType(DataType.Password)]
        [Display(Name = "Новый пароль")]
        public string? NewPassword { get; set; }

        [DataType(DataType.Password)]
        [Display(Name = "Подтвердите новый пароль")]
        [Compare("NewPassword", ErrorMessage = "Пароли не совпадают.")]
        public string? ConfirmPassword { get; set; }

        public string? Token { get; set; }

        public string? UserId { get; set; }
    }
}
