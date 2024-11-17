package com.example.imagesapp.presentation.auth.signup

sealed class SignUpEffect {

    data class ShowToast(val message: String) : SignUpEffect()
    data object NavigateToHome : SignUpEffect()
    data object ShowEmptyEmailMessage : SignUpEffect()
    data object ShowEmptyPasswordMessage : SignUpEffect()
    data object ShowEmptyAgeMessage : SignUpEffect()
    data object ShowInvalidEmailFormatMessage : SignUpEffect()
    data object ShowInvalidPasswordLengthMessage : SignUpEffect()
    data object ShowInvalidAgeRangeMessage : SignUpEffect()

}