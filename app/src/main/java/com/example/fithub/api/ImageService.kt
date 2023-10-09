package com.example.fithub.api

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ImageService {
    @GET("api/images/{imageId}")
    fun getImageById(@Path("imageId") imageId: Int): Call<ResponseBody>
}
