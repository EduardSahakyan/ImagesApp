package com.example.imagesapp.presentation.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imagesapp.domain.resource.Resource
import com.example.imagesapp.domain.resource.Resource.Loading.map
import com.example.imagesapp.domain.usecases.image.LoadImagesUseCase
import com.example.imagesapp.presentation.main.home.models.ImagePreviewUiModel
import com.example.imagesapp.presentation.main.home.models.toPreviewModels
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val loadImagesUseCase: LoadImagesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<List<ImagePreviewUiModel>>(emptyList())
    val uiState = _uiState.asStateFlow()

    private var page: Int = 0
    private var loadingJob: Job? = null

    init {
        loadImages()
    }

    fun loadImages() {
        if (loadingJob != null) return
        loadingJob = loadImagesUseCase(++page, PER_PAGE)
            .map { resource -> resource.map { it.toPreviewModels() } }
            .flowOn(Dispatchers.IO)
            .onEach { resource ->
                when (resource) {
                    Resource.Loading -> Unit
                    is Resource.Error -> page--
                    is Resource.Success -> {
                        val current = uiState.value.toMutableList()
                        current.addAll(resource.data)
                        _uiState.update { current }
                    }
                }
            }
            .flowOn(Dispatchers.Main)
            .onCompletion { loadingJob = null }
            .launchIn(viewModelScope)
    }

    companion object {
        private const val PER_PAGE = 5
    }

}