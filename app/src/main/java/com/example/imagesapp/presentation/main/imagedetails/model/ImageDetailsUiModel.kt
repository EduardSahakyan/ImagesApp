package com.example.imagesapp.presentation.main.imagedetails.model

data class ImageDetailsUiModel(
    val id: Long,
    val owner: String,
    val imageUrl: String,
    val type: String,
    val imageSize: Double,
    val views: Long,
    val likes: Long,
    val comments: Long,
    val downloads: Long,
    val favorites: Long,
    val tags: String
)