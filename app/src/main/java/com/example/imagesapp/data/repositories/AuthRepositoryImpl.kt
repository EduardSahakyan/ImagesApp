package com.example.imagesapp.data.repositories

import com.example.imagesapp.data.local.UserDao
import com.example.imagesapp.data.mappers.toUserEntity
import com.example.imagesapp.domain.models.auth.SignInModel
import com.example.imagesapp.domain.models.auth.SignUpModel
import com.example.imagesapp.domain.repositories.AuthRepository
import com.example.imagesapp.domain.resource.Resource
import com.example.imagesapp.domain.resource.RootError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val userDao: UserDao
) : AuthRepository {

    override fun signUp(signUpModel: SignUpModel): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading)
        if (userDao.isEmailUsed(signUpModel.email)) {
            emit(Resource.Error(RootError.InvalidCredentials))
        } else {
            userDao.insertUser(signUpModel.toUserEntity())
            emit(Resource.Success(Unit))
        }
    }

    override fun signIn(signInModel: SignInModel): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading)
        if (userDao.isUserValid(signInModel.email, signInModel.password)) {
            emit(Resource.Success(Unit))
        } else {
            emit(Resource.Error((RootError.InvalidCredentials)))
        }
    }

}