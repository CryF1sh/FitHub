package com.example.fithub.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fithub.R
import com.example.fithub.api.PostAdapter
import com.example.fithub.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeViewModel: HomeViewModel
    private val postAdapter = PostAdapter(emptyList())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        binding.recyclerViewPosts.adapter = postAdapter
        binding.recyclerViewPosts.layoutManager = LinearLayoutManager(requireContext())

        homeViewModel.posts.observe(viewLifecycleOwner) { posts ->
            postAdapter.posts = posts
            postAdapter.notifyDataSetChanged()
        }

        homeViewModel.loadPosts()

        val createPostButton = view.findViewById<Button>(R.id.createPostButton)
        createPostButton.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToCreatePostFragment()
            findNavController().navigate(action)
        }

        return view
    }
}