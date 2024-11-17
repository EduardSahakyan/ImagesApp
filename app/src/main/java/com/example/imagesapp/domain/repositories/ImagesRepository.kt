package com.example.imagesapp.domain.repositories

import com.example.imagesapp.domain.models.image.Image
import com.example.imagesapp.domain.resource.Resource
import kotlinx.coroutines.flow.Flow

interface ImagesRepository {

    fun loadImages(page: Int, perPage: Int): Flow<Resource<List<Image>>>

    fun getImageById(id: Long): Flow<Resource<Image>>

}