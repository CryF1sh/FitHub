package com.example.fithub.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fithub.models.Post
import com.example.fithub.api.ServiceGenerator
import com.example.fithub.models.PostCreate
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {
    private val _posts = MutableLiveData<List<Post>>()
    val posts: LiveData<List<Post>> = _posts

    private val postService = ServiceGenerator.postService

    fun loadPosts() {
        postService.getPosts().enqueue(object : Callback<List<Post>> {
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                if (response.isSuccessful) {
                    _posts.postValue(response.body())
                } else {
                    // Обработка ошибки при загрузке постов
                }
            }

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                // Обработка ошибки при сетевом запросе
            }
        })
    }
    fun createPost(title: String, content: String, jwtToken: String?, callback: (Boolean) -> Unit) {
        val newPost = PostCreate(title = title, content = content)

        val postService = ServiceGenerator.postService

        val headers = HashMap<String, String>()
        jwtToken?.let { token ->
            headers["Authorization"] = "Bearer $token"
        }

        postService.createPost(newPost, headers).enqueue(object : Callback<Post> {
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                if (response.isSuccessful) {
                    // Статья успешно создана
                    callback(true)
                } else {
                    // Обработка ошибки при создании статьи
                    callback(false)
                }
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
                // Обработка ошибки при сетевом запросе
                callback(false)
            }
        })
    }
}
