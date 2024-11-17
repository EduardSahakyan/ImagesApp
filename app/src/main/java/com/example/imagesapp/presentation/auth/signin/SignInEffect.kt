package com.example.imagesapp.presentation.auth.signin

sealed class SignInEffect {

    data class ShowToast(val message: String) : SignInEffect()
    data object NavigateToHome : SignInEffect()
    data object ShowEmptyEmailMessage : SignInEffect()
    data object ShowEmptyPasswordMessage : SignInEffect()
    data object ShowInvalidEmailFormatMessage : SignInEffect()
    data object ShowInvalidPasswordLengthMessage : SignInEffect()

}