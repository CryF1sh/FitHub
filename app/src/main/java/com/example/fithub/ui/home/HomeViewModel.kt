package com.example.fithub.ui.home

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fithub.models.Post
import com.example.fithub.api.ServiceGenerator
import com.example.fithub.models.PostCreate
import com.example.fithub.models.PostDetails
import com.example.fithub.models.UploadResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import kotlin.io.path.Path
import kotlin.io.path.absolutePathString

class HomeViewModel(private val context: Context) : ViewModel() {
    private val _posts = MutableLiveData<List<Post>>()
    val posts: LiveData<List<Post>> = _posts
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val postService = ServiceGenerator.postService
    private val imageService = ServiceGenerator.imageService

    private var isDataLoaded = false

    fun loadPosts() {
        if (isDataLoaded) {
            return
        }

        _isLoading.postValue(true)

        postService.getPosts(1).enqueue(object : Callback<List<Post>> {
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                if (response.isSuccessful) {
                    val posts = response.body() ?: emptyList()
                    _posts.postValue(posts)
                } else {
                    // Обработка ошибки при загрузке постов
                }

                _isLoading.postValue(false)
                isDataLoaded = true
            }

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                // Обработка ошибки при сетевом запросе
                _isLoading.postValue(false)
            }
        })
    }

    fun createPost(title: String, content: String, jwtToken: String?, titleImageId: Int?, callback: (Boolean) -> Unit) {
        val newPost = PostCreate(title = title, content = content, titleImageId = titleImageId)



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
    fun uploadImage(imageUri: Uri, jwtToken: String?, callback: (Int?) -> Unit) {
        val headers = HashMap<String, String>()
        jwtToken?.let { token ->
            headers["Authorization"] = "Bearer $token"
        }

        val imageFilePath = getRealPathFromURI(imageUri)
        val imageFile = File(imageFilePath)
        //val imageFile = File(imageUri.path) // Тут у меня ошибка на получение полного пути файла

        if (!imageFile.exists()) {
            callback(null)
            return
        }

        val requestFile = imageFile.asRequestBody("image/*".toMediaTypeOrNull())
        val body = MultipartBody.Part.createFormData("file", imageFile.name, requestFile) //name: "image" было

        imageService.uploadImage(body, headers).enqueue(object : Callback<UploadResponse> {
            override fun onResponse(call: Call<UploadResponse>, response: Response<UploadResponse>) {
                if (response.isSuccessful) {
                    val uploadResponse = response.body()
                    if (uploadResponse != null) {
                        val imageId = uploadResponse.imageId
                        callback(imageId)
                    } else {
                        Log.e("UploadImage", "Response body is null")
                        callback(null)
                    }
                } else {
                    Log.e("UploadImage", "Response unsuccessful: ${response.code()}")
                    callback(null)
                }
            }


            override fun onFailure(call: Call<UploadResponse>, t: Throwable) {
                Log.e("UploadImage", "Upload failed", t)
                callback(null)
            }
        })

    }

    fun getPostDetails(postId: Int, callback: (PostDetails?) -> Unit) {
        postService.getPostById(postId).enqueue(object : Callback<PostDetails> {
            override fun onResponse(call: Call<PostDetails>, response: Response<PostDetails>) {
                if (response.isSuccessful) {
                    val postDetails = response.body()
                    callback(postDetails)
                } else {
                    callback(null)
                }
            }

            override fun onFailure(call: Call<PostDetails>, t: Throwable) {
                callback(null)
            }
        })
    }

    fun getRealPathFromURI(uri: Uri): String? {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = context.contentResolver.query(uri, projection, null, null, null)
        return if (cursor != null) {
            val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            val filePath = cursor.getString(columnIndex)
            cursor.close()
            filePath
        } else {
            null
        }
    }


}
