package com.example.fithub.ui.home

import SharedPreferencesManager
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.fithub.databinding.FragmentCreatePostBinding
import com.example.fithub.models.UploadResponse

class CreatePostFragment : Fragment() {
    private lateinit var binding: FragmentCreatePostBinding
    private lateinit var homeViewModel: HomeViewModel

    private var selectedTitleImageUri: Uri? = null
    private val getTitleContent =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.data?.let { uri ->
                    selectedTitleImageUri = uri
                    binding.imageViewTitleImage.setImageURI(uri)
                }
            }
        }

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
        val chooseImageButton = binding.buttonChooseImage

        val sharedPreferencesManager = SharedPreferencesManager(requireContext())

        chooseImageButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            getTitleContent.launch(intent)
        }

        createPostButton.setOnClickListener {
            val title = titleEditText.text.toString()
            val content = contentEditText.text.toString()
            val jwtToken = sharedPreferencesManager.getAuthToken()
            selectedTitleImageUri?.let { uri ->
                homeViewModel.uploadImage(uri, jwtToken) { uploadResponse ->
                    if (uploadResponse != null) {
                        val titleImageId = uploadResponse.imageId
                        homeViewModel.createPost(title, content, jwtToken, titleImageId) { success ->
                            if (success) {
                                showToast("Статья успешно создана")
                                findNavController().navigateUp()
                            } else {
                                showToast("Ошибка при создании статьи")
                            }
                        }
                    } else {
                        showToast("Ошибка при загрузке изображения")
                    }
                }
            } ?: run {
                homeViewModel.createPost(title, content, jwtToken, titleImageId = null) { success ->
                    if (success) {
                        showToast("Статья успешно создана")
                        findNavController().navigateUp()
                    } else {
                        // Обработка ошибки при создании статьи
                        showToast("Ошибка при создании статьи")
                    }
                }
            }
        }

        return view
    }
    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
