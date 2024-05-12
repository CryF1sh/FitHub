package com.example.fithub.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceGenerator {
    //private const val BASE_URL = "http://vpmt.ru:50805"
    private const val BASE_URL = "http://192.168.43.164:5082/"

    private val interceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(interceptor)

        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getBaseUrl(): String {
        return BASE_URL
    }


    val authService: AuthService = retrofit.create(AuthService::class.java)
    val postService: PostService = retrofit.create(PostService::class.java)
    val imageService: ImageService = retrofit.create(ImageService::class.java)
    val workoutService: WorkoutService = retrofit.create(WorkoutService::class.java)
}