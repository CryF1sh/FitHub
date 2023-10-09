package com.example.fithub.ui.home

import SharedPreferencesManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.fithub.databinding.FragmentCreatePostBinding

class CreatePostFragment : Fragment() {
    private lateinit var binding: FragmentCreatePostBinding
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreatePostBinding.inflate(inflater, container, false)
        val view = binding.root

        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        val titleEditText = binding.editTextTitle
        val contentEditText = binding.editTextContent
        val createPostButton = binding.buttonCreatePost

        val sharedPreferencesManager = SharedPreferencesManager(requireContext())

        createPostButton.setOnClickListener {
            val title = titleEditText.text.toString()
            val content = contentEditText.text.toString()
            val jwtToken = sharedPreferencesManager.getAuthToken()

            homeViewModel.createPost(title, content, jwtToken) { success ->
                if (success) {
                    showToast("Статья успешно создана")
                    findNavController().navigateUp()
                } else {
                    // Обработка ошибки при создании статьи
                    showToast("Ошибка при создании статьи")
                }
            }
        }

        return view
    }
    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
