package com.example.fithub.api

import com.example.fithub.models.AuthResponse
import com.example.fithub.models.LoginRequest
import com.example.fithub.models.RegistrationModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("login")
    fun loginUser(@Body loginRequest: LoginRequest): Call<AuthResponse>
    @POST("register")
    fun registerUser(@Body registrationModel: RegistrationModel): Call<AuthResponse>
}