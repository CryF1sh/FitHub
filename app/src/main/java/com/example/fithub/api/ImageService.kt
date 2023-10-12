package com.example.fithub.api

import com.example.fithub.models.UploadResponse
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Header
import retrofit2.http.HeaderMap

interface ImageService {
    @GET("api/images/{imageId}")
    fun getImageById(@Path("imageId") imageId: Int): Call<ResponseBody>

    @Multipart
    @POST("api/images/upload")
    fun uploadImage(
        @Part image: MultipartBody.Part,
        @HeaderMap headers: Map<String, String>
    ): Call<UploadResponse>
}
