package com.example.fithub.ui.home

import SharedPreferencesManager
import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.fithub.databinding.FragmentCreatePostBinding
import com.example.fithub.utils.HomeViewModelFactory

class CreatePostFragment : Fragment() {
    private lateinit var binding: FragmentCreatePostBinding
    private lateinit var homeViewModel: HomeViewModel

    private var selectedTitleImageUri: Uri? = null
    private val chooseImageLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
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

        //homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        homeViewModel = ViewModelProvider(this, HomeViewModelFactory(requireContext())).get(HomeViewModel::class.java)

        val titleEditText = binding.editTextTitle
        val contentEditText = binding.editTextContent
        val createPostButton = binding.buttonCreatePost
        val chooseImageButton = binding.buttonChooseImage

        val sharedPreferencesManager = SharedPreferencesManager(requireContext())

        chooseImageButton.setOnClickListener {
            if (hasStoragePermission()) {
                if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), STORAGE_PERMISSION_REQUEST_CODE)
                } else {
                    startImageSelection()
                }
            } else {
                requestStoragePermission()
            }
        }


        createPostButton.setOnClickListener {
            val title = titleEditText.text.toString()
            val content = contentEditText.text.toString()
            val jwtToken = sharedPreferencesManager.getAuthToken()
            selectedTitleImageUri?.let { uri ->
                homeViewModel.uploadImage(uri, jwtToken) { imageId ->
                    if (imageId != null) {
                        homeViewModel.createPost(title, content, jwtToken, imageId) { success ->
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

    private fun hasStoragePermission(): Boolean {
        return PermissionChecker.checkSelfPermission(
            requireContext(),
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PermissionChecker.PERMISSION_GRANTED
    }

    private fun requestStoragePermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            STORAGE_PERMISSION_REQUEST_CODE
        )
    }

    private fun startImageSelection() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        chooseImageLauncher.launch(intent)
    }

    companion object {
        private const val STORAGE_PERMISSION_REQUEST_CODE = 405
    }
}
