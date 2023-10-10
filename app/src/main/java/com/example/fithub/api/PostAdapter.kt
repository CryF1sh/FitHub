package com.example.fithub.api

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fithub.R
import com.example.fithub.databinding.ItemPostBinding
import com.example.fithub.models.Post

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

            } else {
                // Если изображения нет, установите изображение по умолчанию
                binding.postTitleImage.setImageResource(R.drawable.default_post_image)
            }
        }
    }

    fun clear() {
        posts = emptyList()
        notifyDataSetChanged()
    }
}
