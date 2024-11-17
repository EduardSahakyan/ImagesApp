package com.example.imagesapp.data.repositories

import com.example.imagesapp.data.mappers.toImages
import com.example.imagesapp.data.network.ImagesService
import com.example.imagesapp.domain.models.image.Image
import com.example.imagesapp.domain.repositories.ImagesRepository
import com.example.imagesapp.domain.resource.Resource
import com.example.imagesapp.domain.resource.RootError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ImagesRepositoryImpl @Inject constructor(
    private val imagesService: ImagesService
) : ImagesRepository {

    override fun loadImages(page: Int, perPage: Int): Flow<Resource<List<Image>>> =
        flow {
            emit(Resource.Loading)
            val response = imagesService.getImages(page, perPage)
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(Resource.Success(it.toImages()))
                }
            } else {
                emit(Resource.Error(RootError.ServerError(response.errorBody()?.source()?.readUtf8() ?: "")))
            }
        }.catch {
            emit(Resource.Error(RootError.NetworkError))
        }

    override fun getImageById(id: Long): Flow<Resource<Image>> =
        flow {
            emit(Resource.Loading)
            val response = imagesService.getImageById(id)
            if (response.isSuccessful) {
                response.body()?.let {
                    val image = it.toImages().firstOrNull()
                    if (image != null) {
                        emit(Resource.Success(image))
                    } else {
                        emit(Resource.Error(RootError.UnknownImage))
                    }
                }
            } else {
                emit(Resource.Error(RootError.ServerError(response.errorBody()?.source()?.readUtf8() ?: "")))
            }
        }.catch {
            emit(Resource.Error(RootError.NetworkError))
        }

}