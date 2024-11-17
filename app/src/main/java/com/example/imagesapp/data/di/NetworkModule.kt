package com.example.imagesapp.data.di

import com.example.imagesapp.BuildConfig
import com.example.imagesapp.data.network.BASEURL
import com.example.imagesapp.data.network.ImagesService
import com.example.imagesapp.data.network.PARAM_KEY
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor {
                val originalRequest = it.request()
                val originalUrl = originalRequest.url()
                val newUrl = originalUrl.newBuilder()
                    .addQueryParameter(PARAM_KEY, BuildConfig.PIXBAY_API_KEY)
                    .build()
                val newRequest = originalRequest.newBuilder()
                    .url(newUrl)
                    .build()
                it.proceed(newRequest)
            }
            .build()
    }

    @Provides
    @Singleton
    fun provideImagesService(client: OkHttpClient): ImagesService {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASEURL)
            .client(client)
            .build()
        return retrofit.create()
    }

}