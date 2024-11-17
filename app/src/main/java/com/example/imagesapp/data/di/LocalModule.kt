package com.example.imagesapp.data.di

import android.content.Context
import androidx.room.Room
import com.example.imagesapp.data.local.AppDatabase
import com.example.imagesapp.data.local.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Provides
    @Singleton
    fun provideAuthDao(@ApplicationContext context: Context): UserDao {
        return Room.databaseBuilder(context, AppDatabase::class.java, "users.db").build().userDao()
    }

}