package com.example.imagesapp.presentation.auth.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imagesapp.domain.models.auth.SignUpModel
import com.example.imagesapp.domain.resource.Resource
import com.example.imagesapp.domain.usecases.auth.SignUpUseCase
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
class SignUpViewModel @Inject constructor(
    private val userValidator: UserValidator,
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {

    private val _effects = MutableSharedFlow<SignUpEffect>(replay = 0, extraBufferCapacity = 1)
    val effect = _effects.asSharedFlow()

    private val _uiState = MutableStateFlow(State())
    val uiState = _uiState.asStateFlow()

    fun singUp(email: String, password: String, age: String) {
        _uiState.update { State() }
        val signUpModel = SignUpModel(email, password, age)
        when (userValidator.validateSignUp(signUpModel)) {
            UserValidation.EmptyEmail -> _effects.tryEmit(SignUpEffect.ShowEmptyEmailMessage)
            UserValidation.EmptyPassword -> _effects.tryEmit(SignUpEffect.ShowEmptyPasswordMessage)
            UserValidation.EmptyAge -> _effects.tryEmit(SignUpEffect.ShowEmptyAgeMessage)
            UserValidation.InvalidEmailFormat -> _effects.tryEmit(SignUpEffect.ShowInvalidEmailFormatMessage)
            UserValidation.PasswordLengthInvalid -> _effects.tryEmit(SignUpEffect.ShowInvalidPasswordLengthMessage)
            UserValidation.InvalidAgeRange -> _effects.tryEmit(SignUpEffect.ShowInvalidAgeRangeMessage)
            UserValidation.Valid -> signUp(signUpModel)
        }
    }

    fun setEmailErrorMessage(message: String) {
        _uiState.update { it.copy(emailErrorMessage = message) }
    }

    fun setPasswordErrorMessage(message: String) {
        _uiState.update { it.copy(passwordErrorMessage = message) }
    }

    fun setAgeErrorMessage(message: String) {
        _uiState.update { it.copy(ageErrorMessage = message) }
    }

    private fun signUp(signUpModel: SignUpModel) {
        signUpUseCase(signUpModel)
            .flowOn(Dispatchers.IO)
            .onEach { resource ->
                when (resource) {
                    Resource.Loading -> Unit
                    is Resource.Error -> Unit
                    is Resource.Success -> _effects.emit(SignUpEffect.NavigateToHome)
                }
            }
            .flowOn(Dispatchers.Main)
            .launchIn(viewModelScope)
    }

    data class State(
        val emailErrorMessage: String = "",
        val passwordErrorMessage: String = "",
        val ageErrorMessage: String = ""
    )

}