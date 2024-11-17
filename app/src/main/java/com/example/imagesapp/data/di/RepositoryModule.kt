package com.example.imagesapp.data.di

import com.example.imagesapp.data.repositories.AuthRepositoryImpl
import com.example.imagesapp.data.repositories.ImagesRepositoryImpl
import com.example.imagesapp.domain.repositories.AuthRepository
import com.example.imagesapp.domain.repositories.ImagesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    fun bindImageRepository(impl: ImagesRepositoryImpl): ImagesRepository

}