package com.example.fithub.api

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fithub.R
import com.example.fithub.api.ServiceGenerator
import com.example.fithub.api.ServiceGenerator.imageService
import com.example.fithub.databinding.ItemPostBinding
import com.example.fithub.models.Post
import com.example.fithub.ui.home.PostDetailFragmentDirections
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PostAdapter(var posts: List<Post>) : RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(posts[position])
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    inner class ViewHolder(val binding: ItemPostBinding) : RecyclerView.ViewHolder(binding.root) {

        private val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        private val creatorNameAndDateTextView: TextView =
            itemView.findViewById(R.id.creatorNameAndDateTextView)
        private val postTitleImage: ImageView = itemView.findViewById(R.id.postTitleImage)

        fun bind(post: Post) {
            binding.titleTextView.text = post.title
            val formattedDate = post.getFormattedCreationDate()
            binding.creatorNameAndDateTextView.text = "${post.creatorFirstName} ${post.creatorLastName}, ${formattedDate}"
            if (post.titleImageId != null) {
                Log.e("PostHaveImage", "Post ${post.postId} have image:${post.titleImageId}")
                loadImageForPost(post.titleImageId)
            } else {
                binding.postTitleImage.setImageResource(R.drawable.default_post_image)
            }
            binding.root.setOnClickListener {
                val action = PostDetailFragmentDirections.actionPostsFragmentToPostDetailFragment(post.postId)
                it.findNavController().navigate(action)
            }

        }
        private fun loadImageForPost(imageId: Int) {
            imageService.getImageById(imageId).enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if (response.isSuccessful) {
                        val imageBytes = response.body()?.bytes()
                        if (imageBytes != null) {
                            // Логируем успешную загрузку изображения
                            Log.d("ImageLoading", "Image loaded successfully")
                            Glide.with(itemView.context)
                                .asBitmap()
                                .load(imageBytes)
                                .placeholder(R.drawable.default_post_image)
                                .into(binding.postTitleImage)
                        } else {
                            // Обработка ошибки загрузки бинарных данных изображения
                            Log.e("ImageLoading", "Image bytes are null")
                        }
                    } else {
                        // Обработка ошибки при загрузке изображения
                        Log.e("ImageLoading", "Image loading failed with code: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    // Обработка ошибки при сетевом запросе изображения
                    Log.e("ImageLoading", "Image loading failed with exception: ${t.message}")
                }
            })
        }
    }

    fun clear() {
        posts = emptyList()
        notifyDataSetChanged()
    }
}
