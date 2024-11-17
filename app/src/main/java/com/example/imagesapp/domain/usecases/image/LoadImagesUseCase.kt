package com.example.imagesapp.domain.usecases.image

import com.example.imagesapp.domain.models.image.Image
import com.example.imagesapp.domain.repositories.ImagesRepository
import com.example.imagesapp.domain.resource.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoadImagesUseCase @Inject constructor(
    private val imagesRepository: ImagesRepository
) {

    operator fun invoke(page: Int, perPage: Int): Flow<Resource<List<Image>>> {
        return imagesRepository.loadImages(page, perPage)
    }

}