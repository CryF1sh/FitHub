package com.example.fithub.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fithub.R
import com.example.fithub.api.PostAdapter
import com.example.fithub.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeViewModel: HomeViewModel
    private val postAdapter = PostAdapter(emptyList())
    private lateinit var recyclerView: RecyclerView
    private lateinit var createPostButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root


        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        recyclerView = view.findViewById(R.id.recyclerViewPosts)
        createPostButton = view.findViewById(R.id.createPostButton)

        recyclerView.adapter = postAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        homeViewModel.loadPosts()

        homeViewModel.posts.observe(viewLifecycleOwner) { posts ->
            postAdapter.posts = posts
            postAdapter.notifyDataSetChanged()
        }

        homeViewModel.loadPosts()

        createPostButton.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToCreatePostFragment()
            findNavController().navigate(action)
        }

        val searchView = binding.searchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterPosts(newText)
                return true
            }
        })

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        postAdapter.clear()
    }

    private fun filterPosts(query: String?) {
        val filteredPosts = homeViewModel.posts.value.orEmpty().filter { post ->
            post.title.contains(query.orEmpty(), ignoreCase = true)
        }
        postAdapter.posts = filteredPosts
        postAdapter.notifyDataSetChanged()
    }
}

