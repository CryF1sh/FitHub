package com.example.fithub.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    // Определите метод для выполнения запроса на вход в систему
    @POST("login") // Замените "login" на путь к вашему API для входа
    fun loginUser(@Body loginRequest: LoginRequest): Call<AuthResponse>
}