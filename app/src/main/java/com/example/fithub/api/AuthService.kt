package com.example.fithub.api

import com.example.fithub.models.AuthResponse
import com.example.fithub.models.LoginRequest
import com.example.fithub.models.RegistrationModel
import com.example.fithub.models.UserProfile
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.POST
import retrofit2.http.PUT

interface AuthService {
    @POST("login")
    fun loginUser(@Body loginRequest: LoginRequest): Call<AuthResponse>
    @POST("register")
    fun registerUser(@Body registrationModel: RegistrationModel): Call<AuthResponse>
    @GET("profile")
    fun getProfile(@HeaderMap headers: Map<String, String>): Call<UserProfile>
    @PUT("profile")
    fun updateProfile(@Body profile: UserProfile, @HeaderMap headers: Map<String, String>): Call<Boolean>
}