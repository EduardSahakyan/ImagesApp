package com.example.imagesapp.presentation.main.imagedetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imagesapp.domain.resource.Resource
import com.example.imagesapp.domain.resource.Resource.Loading.map
import com.example.imagesapp.domain.usecases.image.GetImageByIdUseCase
import com.example.imagesapp.presentation.main.imagedetails.model.ImageDetailsUiModel
import com.example.imagesapp.presentation.main.imagedetails.model.toDetailsUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject


@HiltViewModel
class ImageDetailsViewModel @Inject constructor(
    private val getImageByIdUseCase: GetImageByIdUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<ImageDetailsUiModel?>(null)
    val uiState = _uiState.asStateFlow()

    fun getImageById(id: Long) {
        getImageByIdUseCase(id)
            .map { resource -> resource.map { it.toDetailsUiModel() } }
            .flowOn(Dispatchers.IO)
            .onEach { resource ->
                when (resource) {
                    Resource.Loading -> Unit
                    is Resource.Error -> Unit
                    is Resource.Success -> {
                        _uiState.update { resource.data }
                    }
                }
            }
            .flowOn(Dispatchers.Main)
            .launchIn(viewModelScope)
    }

}