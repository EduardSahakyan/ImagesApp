package com.example.imagesapp.data.mappers

import com.example.imagesapp.data.network.responses.ImageResponse
import com.example.imagesapp.data.network.responses.ImagesResponse
import com.example.imagesapp.domain.models.image.Image
import com.example.imagesapp.domain.models.image.ImageAdditionalInfo
import com.example.imagesapp.domain.models.image.ImageSources
import com.example.imagesapp.domain.models.image.ImageType

fun ImagesResponse.toImages() = hits.map { it.toImage() }

fun ImageResponse.toImage() = Image(
    id = id,
    owner = user,
    sources = ImageSources(
        previewUrl = previewURL,
        largeImageUrl = largeImageURL
    ),
    info = ImageAdditionalInfo(
        type = ImageType.entries.firstOrNull { it.name.lowercase() == type } ?: ImageType.VECTOR,
        imageSize = imageSize,
        views = views,
        likes = likes,
        comments = comments,
        downloads = downloads,
        favorites = collections,
        tags = tags.split(TAGS_DELIMITER)
    )
)

private const val TAGS_DELIMITER = ", "