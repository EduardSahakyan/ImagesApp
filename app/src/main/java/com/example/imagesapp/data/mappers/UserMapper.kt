package com.example.imagesapp.data.mappers

import com.example.imagesapp.data.local.entities.UserEntity
import com.example.imagesapp.domain.models.auth.SignUpModel

fun SignUpModel.toUserEntity() = UserEntity(
    id = 0,
    email = email,
    password = password,
    age = age.toInt()
)