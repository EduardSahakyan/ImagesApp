package com.example.imagesapp.presentation.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.imagesapp.databinding.FragmentHomeBinding
import com.example.imagesapp.presentation.main.home.adapters.ImagesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
        get() = _binding ?: throw IllegalStateException("Binding is null")

    private val viewModel: HomeViewModel by viewModels()

    private val imagesAdapter by lazy {
        ImagesAdapter(
            onItemClick = { id ->
                navigateToDetails(id)
            }
        )
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        collectImages()
    }

    private fun loadImages() {
        viewModel.loadImages()
    }

    private fun collectImages() {
        lifecycleScope.launch {
            viewModel.uiState.collectLatest { data ->
                imagesAdapter.submitList(data)
            }
        }
    }

    private fun setupView() = with(binding) {
        rvImages.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = imagesAdapter

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                    val totalItemCount = layoutManager.itemCount

                    if (lastVisibleItemPosition == totalItemCount - 1) {
                        viewModel.loadImages()
                    }
                }
            })
        }
    }

    private fun navigateToDetails(id: Long) {
        val navController = findNavController()
        navController.navigate(HomeFragmentDirections.actionHomeFragmentToImageDetailsFragment(id))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}