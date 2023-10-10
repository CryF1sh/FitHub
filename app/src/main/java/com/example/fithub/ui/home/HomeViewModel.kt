package com.example.fithub.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fithub.models.Post
import com.example.fithub.api.ServiceGenerator
import com.example.fithub.models.PostCreate
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {
    private val _posts = MutableLiveData<List<Post>>()
    val posts: LiveData<List<Post>> = _posts
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val postService = ServiceGenerator.postService
    private val imageService = ServiceGenerator.imageService

    private var currentPage = 1
    var isLastPage = false
    private var isLoadingData = false

    fun loadPosts() {
        if (isLoadingData || isLastPage) {
            return
        }

        _isLoading.postValue(true)

        postService.getPosts(currentPage).enqueue(object : Callback<List<Post>> {
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                if (response.isSuccessful) {
                    val posts = response.body() ?: emptyList()

                    if (posts.isNotEmpty()) {
                        currentPage++
                        val currentPosts = _posts.value.orEmpty().toMutableList()
                        currentPosts.addAll(posts)
                        _posts.postValue(currentPosts)
                    } else {
                        isLastPage = true
                    }
                } else {
                    // Обработка ошибки при загрузке постов
                }

                _isLoading.postValue(false)
                isLoadingData = false
            }

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                // Обработка ошибки при сетевом запросе
                _isLoading.postValue(false)
                isLoadingData = false
            }
        })
    }

    private fun loadImageForPost(post: Post, imageId: Int) {
        imageService.getImageById(imageId).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    // Получите изображение и установите его в пост
                    val imageBytes = response.body()?.bytes()
                    if (imageBytes != null) {
                        // Преобразуйте imageBytes в Bitmap и установите его в post
                        // Например, можно использовать BitmapFactory.decodeByteArray()
                        // и установить изображение в post.titleImage
                    }
                } else {
                    // Обработка ошибки при загрузке изображения
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                // Обработка ошибки при сетевом запросе изображения
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
