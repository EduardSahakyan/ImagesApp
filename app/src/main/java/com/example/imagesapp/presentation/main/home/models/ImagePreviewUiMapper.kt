package com.example.imagesapp.presentation.main.home.models

import com.example.imagesapp.domain.models.image.Image

fun Image.toPreviewUiModel() = ImagePreviewUiModel(
    id = id,
    owner = owner,
    imageUrl = sources.previewUrl
)

fun List<Image>.toPreviewModels() = map { it.toPreviewUiModel() }