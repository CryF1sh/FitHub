﻿using FitHub.Data;
using FitHub.Web.Modeles.Identity;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using FitHub.Web.Services;


namespace FitHub.Web.Controllers
{
    public class AccountController : Controller
    {
        private readonly UserManager<ApplicationUser> _userManager;
        private readonly SignInManager<ApplicationUser> _signInManager;
        private readonly JwtService _jwtService;

        public AccountController(UserManager<ApplicationUser> userManager, SignInManager<ApplicationUser> signInManager, JwtService jwtService)
        {
            _userManager = userManager;
            _signInManager = signInManager;
            _jwtService = jwtService;
        }

        [HttpPost("register")]
        public async Task<IActionResult> Register([FromBody] RegistrationModel model)
        {
            if (ModelState.IsValid)
            {
                // Проверка уникальности email
                var existingUser = await _userManager.FindByEmailAsync(model.Email);
                if (existingUser != null)
                {
                    return BadRequest(new { Errors = new List<string> { "Email уже зарегистрирован" } });
                }

                var user = new ApplicationUser
                {
                    Email = model.Email,
                    Firstname = model.Firstname,
                    Lastname = model.Lastname,
                    Birthdate = model.Birthdate,
                    Location = model.Location
                    //Usersports 
                };

                var result = await _userManager.CreateAsync(user, model.Password);

                if (result.Succeeded)
                {
                    var token = _jwtService.GenerateToken(user);

                    // Возвращаем токен в ответе
                    return Ok(new AuthResponse { Token = token });
                }
                else
                {
                    return BadRequest(new { Errors = result.Errors });
                }
            }

            return BadRequest(ModelState);
        }
    }
}
