package com.example.fithub.models

data class RegistrationModel(
    //@field:NotEmpty(message = "Email обязателен")
    //@field:Email(message = "Введите действительный Email адрес")
    val email: String,

    //@field:NotEmpty(message = "Пароль обязателен")
    //@field:Size(min = 6, message = "Пароль должен содержать не менее 6 символов")
    val password: String,

    //@field:NotEmpty(message = "Подтвердите пароль")
    val confirmPassword: String,

    //@field:NotEmpty(message = "Имя обязательно")
    val firstname: String,

    //@field:NotEmpty(message = "Фамилия обязательна")
    val lastname: String,

    val birthdate: String? = null,

    val location: String? = null
)
