package com.example.fithub.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.fithub.databinding.FragmentPostDetailBinding
import com.example.fithub.utils.HomeViewModelFactory
import io.noties.markwon.Markwon
import io.noties.markwon.image.coil.CoilImagesPlugin

class PostDetailFragment : Fragment() {
    private lateinit var binding: FragmentPostDetailBinding
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPostDetailBinding.inflate(inflater, container, false)
        val view = binding.root

        val markwon = Markwon.builder(requireContext())
            .usePlugin(CoilImagesPlugin.create(requireContext()))
            .build()

        homeViewModel = ViewModelProvider(this, HomeViewModelFactory(requireContext())).get(HomeViewModel::class.java)

        val postId = arguments?.getInt("postId") ?: 0

        val textViewTitle = binding.textViewTitle
        val textViewContent = binding.textViewContent
        val textViewCreatorName = binding.textViewCreatorName
        val textViewDate = binding.textViewDate

        homeViewModel.getPostDetails(postId) { postDetails ->
            textViewTitle.text = postDetails?.title
            markwon.setMarkdown(textViewContent, postDetails?.content ?: "")
            textViewCreatorName.text = "${postDetails?.creatorFirstName} ${postDetails?.creatorLastName}"
            textViewDate.text = postDetails?.getFormattedCreationDate()
        }

        return view
    }
}
