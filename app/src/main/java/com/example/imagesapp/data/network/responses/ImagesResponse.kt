package com.example.imagesapp.data.network.responses

class ImagesResponse(
    val hits: List<ImageResponse>
)

class ImageResponse(
    val id: Long,
    val user: String,
    val type: String,
    val tags: String,
    val previewURL: String,
    val largeImageURL: String,
    val imageSize: Long,
    val views: Long,
    val likes: Long,
    val comments: Long,
    val downloads: Long,
    val collections: Long
)