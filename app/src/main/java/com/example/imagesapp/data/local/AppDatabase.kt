package com.example.imagesapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.imagesapp.data.local.entities.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}