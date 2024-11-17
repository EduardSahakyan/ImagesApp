package com.example.imagesapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("users")
class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val email: String,
    val password: String,
    val age: Int
)