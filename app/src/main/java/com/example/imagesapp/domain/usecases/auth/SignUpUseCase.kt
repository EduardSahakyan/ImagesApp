package com.example.imagesapp.domain.usecases.auth

import com.example.imagesapp.domain.models.auth.SignUpModel
import com.example.imagesapp.domain.repositories.AuthRepository
import com.example.imagesapp.domain.resource.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    operator fun invoke(signUpModel: SignUpModel): Flow<Resource<Unit>> {
        return authRepository.signUp(signUpModel)
    }

}