package com.example.imagesapp.data.network

import com.example.imagesapp.data.network.responses.ImagesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ImagesService {

    @GET(ENDPOINT_IMAGES)
    suspend fun getImages(
        @Query(PARAM_PAGE) page: Int,
        @Query(PARAM_PER_PAGE) perPage: Int
    ): Response<ImagesResponse>

    @GET(ENDPOINT_IMAGES)
    suspend fun getImageById(@Query(PARAM_ID) id: Long): Response<ImagesResponse>

}