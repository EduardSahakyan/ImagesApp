package com.example.imagesapp.presentation.main.imagedetails.model

import com.example.imagesapp.domain.models.image.Image
import java.util.Locale

fun Image.toDetailsUiModel() = ImageDetailsUiModel(
    id = id,
    owner = owner,
    imageUrl = sources.largeImageUrl,
    type = info.type.name.lowercase().replaceFirstChar { it.titlecase(Locale.getDefault()) },
    imageSize = bytesToMb(info.imageSize),
    views = info.views,
    likes = info.likes,
    comments = info.comments,
    downloads = info.downloads,
    favorites = info.favorites,
    tags = info.tags.joinToString(TAGS_SEPARATOR)
)

private fun bytesToMb(bytes: Long): Double {
    return bytes / (BYTES_PER_KB * KB_PER_MB)
}

private const val TAGS_SEPARATOR = ", "
private const val BYTES_PER_KB = 1024.0
private const val KB_PER_MB = 1024.0