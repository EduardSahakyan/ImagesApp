package com.example.imagesapp.presentation.auth.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imagesapp.domain.models.auth.SignInModel
import com.example.imagesapp.domain.resource.Resource
import com.example.imagesapp.domain.usecases.auth.SignInUseCase
import com.example.imagesapp.presentation.auth.utils.UserValidation
import com.example.imagesapp.presentation.auth.utils.UserValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val userValidator: UserValidator,
    private val signInUseCase: SignInUseCase
) : ViewModel() {

    private val _effects = MutableSharedFlow<SignInEffect>(replay = 0, extraBufferCapacity = 1)
    val effect = _effects.asSharedFlow()

    private val _uiState = MutableStateFlow(State())
    val uiState = _uiState.asStateFlow()

    fun signIn(email: String, password: String) {
        _uiState.update { State() }
        val signInModel = SignInModel(email, password)
        when (userValidator.validateSignIn(signInModel)) {
            UserValidation.EmptyEmail -> _effects.tryEmit(SignInEffect.ShowEmptyEmailMessage)
            UserValidation.EmptyPassword -> _effects.tryEmit(SignInEffect.ShowEmptyPasswordMessage)
            UserValidation.InvalidEmailFormat -> _effects.tryEmit(SignInEffect.ShowInvalidEmailFormatMessage)
            UserValidation.PasswordLengthInvalid -> _effects.tryEmit(SignInEffect.ShowInvalidPasswordLengthMessage)
            UserValidation.Valid -> signIn(signInModel)
            else -> Unit
        }
    }

    fun setEmailErrorMessage(message: String) {
        _uiState.update { it.copy(emailErrorMessage = message) }
    }

    fun setPasswordErrorMessage(message: String) {
        _uiState.update { it.copy(passwordErrorMessage = message) }
    }

    private fun signIn(signInModel: SignInModel) {
        signInUseCase(signInModel)
            .flowOn(Dispatchers.IO)
            .onEach { resource ->
                when (resource) {
                    Resource.Loading -> Unit
                    is Resource.Error -> Unit
                    is Resource.Success -> _effects.emit(SignInEffect.NavigateToHome)
                }
            }
            .flowOn(Dispatchers.Main)
            .launchIn(viewModelScope)
    }

    data class State(
        val emailErrorMessage: String = "",
        val passwordErrorMessage: String = ""
    )

}