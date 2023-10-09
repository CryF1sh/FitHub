package com.example.fithub.api

import com.example.fithub.models.Post
import com.example.fithub.models.PostCreate
import com.example.fithub.models.PostUpdate
import retrofit2.Call
import retrofit2.http.*

interface PostService {
    @GET("api/posts")
    fun getPosts(): Call<List<Post>>

    @GET("api/posts/{id}")
    fun getPostById(@Path("id") postId: Int): Call<Post>

    @POST("api/posts")
    fun createPost(@Body post: PostCreate, @HeaderMap headers: Map<String, String>): Call<Post>

    @PUT("api/posts/{id}")
    fun updatePost(@Path("id") postId: Int, @Body post: PostUpdate): Call<Void>

    @DELETE("api/posts/{id}")
    fun deletePost(@Path("id") postId: Int): Call<Void>
}
