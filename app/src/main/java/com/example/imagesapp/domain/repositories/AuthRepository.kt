package com.example.imagesapp.domain.repositories

import com.example.imagesapp.domain.models.auth.SignInModel
import com.example.imagesapp.domain.models.auth.SignUpModel
import com.example.imagesapp.domain.resource.Resource
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    fun signUp(signUpModel: SignUpModel): Flow<Resource<Unit>>

    fun signIn(signInModel: SignInModel): Flow<Resource<Unit>>

}