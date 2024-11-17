package com.example.imagesapp.domain.models.image

class Image(
    val id: Long,
    val owner: String,
    val sources: ImageSources,
    val info: ImageAdditionalInfo
)

class ImageAdditionalInfo(
    val type: ImageType,
    val imageSize: Long,
    val views: Long,
    val likes: Long,
    val comments: Long,
    val downloads: Long,
    val favorites: Long,
    val tags: List<String>,
)

class ImageSources(
    val previewUrl: String,
    val largeImageUrl: String,
)

enum class ImageType {
    VECTOR, PHOTO, ILLUSTRATION
}