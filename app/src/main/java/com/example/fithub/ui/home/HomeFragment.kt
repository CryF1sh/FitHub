package com.example.fithub.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.fithub.R
import com.example.fithub.api.PostAdapter
import com.example.fithub.databinding.FragmentHomeBinding
import com.example.fithub.utils.HomeViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeViewModel: HomeViewModel
    private val postAdapter = PostAdapter(emptyList())
    private lateinit var recyclerView: RecyclerView
    private lateinit var createPostButton: Button
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private var currentPage = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root


        //homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        homeViewModel = ViewModelProvider(this, HomeViewModelFactory(requireContext())).get(HomeViewModel::class.java)

        recyclerView = view.findViewById(R.id.recyclerViewPosts)
//        createPostButton = view.findViewById(R.id.createPostButton)
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout)


        recyclerView.adapter = postAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        homeViewModel.loadPosts(1)

        homeViewModel.posts.observe(viewLifecycleOwner) { posts ->
            if (posts != null) {
                postAdapter.posts = posts
            }
            postAdapter.notifyDataSetChanged()
        }

        //homeViewModel.loadPosts()

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    homeViewModel.loadPosts(currentPage++)
                }
            }
        })

        swipeRefreshLayout.setOnRefreshListener {
            currentPage = 1
            homeViewModel.clearPosts()
            homeViewModel.loadPosts(currentPage)
            swipeRefreshLayout.isRefreshing = false
        }

//        createPostButton.setOnClickListener {
//            val action = HomeFragmentDirections.actionHomeFragmentToCreatePostFragment()
//            findNavController().navigate(action)
//        }

        val scrollToTopButton: FloatingActionButton = view.findViewById(R.id.scrollToTopButton)
        scrollToTopButton.setOnClickListener {
            recyclerView.smoothScrollToPosition(0)
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createPostButton = view.findViewById(R.id.createPostButton)
        val navController = findNavController()
        createPostButton.setOnClickListener {
            navController.navigate(R.id.createPostFragment)
        }
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

