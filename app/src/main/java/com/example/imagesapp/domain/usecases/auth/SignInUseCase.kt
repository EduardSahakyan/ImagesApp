package com.example.imagesapp.domain.usecases.auth

import com.example.imagesapp.domain.models.auth.SignInModel
import com.example.imagesapp.domain.repositories.AuthRepository
import com.example.imagesapp.domain.resource.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    operator fun invoke(signInModel: SignInModel): Flow<Resource<Unit>> {
        return authRepository.signIn(signInModel)
    }

}