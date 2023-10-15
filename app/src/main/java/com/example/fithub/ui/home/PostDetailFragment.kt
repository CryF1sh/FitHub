package com.example.fithub.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.fithub.databinding.FragmentPostDetailBinding
import com.example.fithub.utils.HomeViewModelFactory

class PostDetailFragment : Fragment() {
    private lateinit var binding: FragmentPostDetailBinding
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPostDetailBinding.inflate(inflater, container, false)
        val view = binding.root

        //homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        homeViewModel = ViewModelProvider(this, HomeViewModelFactory(requireContext())).get(HomeViewModel::class.java)

        val postId = arguments?.getInt("postId") ?: 0

        homeViewModel.getPostDetails(postId) { postDetails ->
            binding.textViewTitle.text = postDetails?.title
            binding.textViewContent.text = postDetails?.content
            binding.textViewCreatorName.text = "${postDetails?.creatorFirstName} ${postDetails?.creatorLastName}"
            binding.textViewDate.text = postDetails?.getFormattedCreationDate()
        }

        return view
    }
}
