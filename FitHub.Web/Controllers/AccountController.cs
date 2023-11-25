﻿using FitHub.Data;
using FitHub.Web.Modeles.Identity;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using FitHub.Web.Services;
using Microsoft.AspNetCore.Identity.UI.Services;
using FitHub.Web.Interfaces;

namespace FitHub.Web.Controllers
{
    public class AccountController : Controller
    {
        private readonly UserManager<ApplicationUser> _userManager;
        private readonly SignInManager<ApplicationUser> _signInManager;
        private readonly JwtService _jwtService;
        private readonly ISendGridEmail _emailSender;
        private readonly ILogger<AccountController> _logger;

        public AccountController(UserManager<ApplicationUser> userManager, SignInManager<ApplicationUser> signInManager, JwtService jwtService, ILogger<AccountController> logger, ISendGridEmail emailSender)
        {
            _userManager = userManager;
            _signInManager = signInManager;
            _jwtService = jwtService;
            _logger = logger;
            _emailSender = emailSender;
        }

        [HttpPost("register")]
        public async Task<IActionResult> Register([FromBody] RegistrationModel model)
        {
            if (ModelState.IsValid)
            {
                try
                    {
                    // Проверка уникальности email
                    var existingUser = await _userManager.FindByEmailAsync(model.Email);
                    if (existingUser != null)
                    {
                        return BadRequest(new { Errors = new List<string> { "Email уже зарегистрирован" } });
                    }

                    var user = new ApplicationUser
                    {
                        UserName = model.Email,
                        Email = model.Email,
                        Firstname = model.Firstname,
                        Lastname = model.Lastname,
                        Birthdate = model.Birthdate,
                        //Location = model.Location,
                        Registrationdate = DateTime.Now
                        //Usersports 
                    };

                    var result = await _userManager.CreateAsync(user, model.Password);

                    if (result.Succeeded)
                    {
                        //Для мобильного приложения
                        var token = _jwtService.GenerateToken(user);
                        return Ok(new AuthResponse { Token = token });
                    }
                    else
                    {
                        return BadRequest(new { Errors = result.Errors });
                    }
                }
                 catch (Exception ex)
        {
                    _logger.LogError(ex, "Ошибка при регистрации пользователя.");
                    return StatusCode(500, "Произошла ошибка при регистрации пользователя. Пожалуйста, попробуйте еще раз.");
                }
            }

            return BadRequest(ModelState);
        }

        [HttpPost("login")]
        public async Task<IActionResult> Login([FromBody] LoginModel model)
        {
            if (ModelState.IsValid)
            {
                var user = await _userManager.FindByEmailAsync(model.Email);
                try
                {
                    if (user == null)
                    {
                        _logger.LogError($"Попытка входа с неверным Email: {model.Email}");
                        return BadRequest(new { Errors = new List<string> { "Неверный Email или пароль" } });
                    }

                    var result = await _signInManager.PasswordSignInAsync(user, model.Password, false, false);

                    if (result.Succeeded)
                    {
                        var token = _jwtService.GenerateToken(user);
                        _logger.LogInformation($"Пользователь {user.UserName} успешно вошел в систему.");
                        return Ok(new AuthResponse { Token = token });
                    }
                    else
                    {
                        _logger.LogError($"Ошибка входа пользователя {user.UserName}. Неверный пароль.");
                        return BadRequest(new { Errors = new List<string> { "Неверный Email или пароль" } });
                    }
                }
                catch (Exception ex)
                {
                    _logger.LogError(ex, $"Ошибка при входе в систему. Логин пользователя {user.UserName}");
                    return StatusCode(500, "Произошла ошибка при входе в систему. Пожалуйста, попробуйте еще раз.");
                }
            }

            return BadRequest(ModelState);
        }

        [HttpPost("forgot-password")]
        public async Task<IActionResult> ForgotPassword([FromBody] ForgotPasswordModel model)
        {
            if (ModelState.IsValid)
            {
                var user = await _userManager.FindByEmailAsync(model.Email);
                if (user != null)
                {
                    var token = await _userManager.GeneratePasswordResetTokenAsync(user);
                    var callbackUrl = Url.Action(
                        "ResetPassword",
                        "Account",
                        new { userId = user.Id, token = token },
                        protocol: HttpContext.Request.Scheme);

                    try
                    {
                        await _emailSender.SendEmailAsync(
                            model.Email,
                            "Восстановление пароля",
                            $"Для сброса пароля перейдите по <a href='{callbackUrl}'>ссылке</a>.");

                        return Ok("Письмо с инструкциями по сбросу пароля отправлено на указанный email.");
                    }
                    catch (Exception ex)
                    {
                        return StatusCode(500, "Произошла ошибка при отправке email. Пожалуйста, попробуйте еще раз.");
                    }
                }
                else
                {
                    return NotFound("Пользователь с указанным email не найден.");
                }
            }
            return BadRequest(ModelState);
        }


        [HttpPost("reset-password")]
        public async Task<IActionResult> ResetPassword([FromBody] ResetPasswordModel model)
        {
            if (ModelState.IsValid)
            {
                var user = await _userManager.FindByEmailAsync(model.Email);
                if (user != null)
                {
                    var result = await _userManager.ResetPasswordAsync(user, model.Token, model.NewPassword);
                    if (result.Succeeded)
                    {
                        return Ok("Пароль успешно сброшен.");
                    }
                    else
                    {
                        return BadRequest("Ошибка сброса пароля.");
                    }
                }
                else
                {
                    return NotFound("Пользователь с указанным email не найден.");
                }
            }
            return BadRequest(ModelState);
        }

    }
}
