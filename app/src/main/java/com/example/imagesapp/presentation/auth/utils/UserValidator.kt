package com.example.imagesapp.presentation.auth.utils

import com.example.imagesapp.domain.models.auth.SignInModel
import com.example.imagesapp.domain.models.auth.SignUpModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserValidator @Inject constructor() {

    private val emailPattern = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")

    fun validateSignIn(signInModel: SignInModel): UserValidation {
        if (signInModel.email.isBlank()) {
            return UserValidation.EmptyEmail
        } else if (!signInModel.email.matches(emailPattern)) {
            return UserValidation.InvalidEmailFormat
        }

        if (signInModel.password.isBlank()) {
            return UserValidation.EmptyPassword
        } else if (signInModel.password.length !in PASSWORD_MIN_LENGTH..PASSWORD_MAX_LENGTH) {
            return UserValidation.PasswordLengthInvalid
        }

        return UserValidation.Valid
    }

    fun validateSignUp(signUpModel: SignUpModel): UserValidation {
        if (signUpModel.email.isBlank()) {
            return UserValidation.EmptyEmail
        } else if (!signUpModel.email.matches(emailPattern)) {
            return UserValidation.InvalidEmailFormat
        }

        if (signUpModel.password.isBlank()) {
            return UserValidation.EmptyPassword
        } else if (signUpModel.password.length !in PASSWORD_MIN_LENGTH..PASSWORD_MAX_LENGTH) {
            return UserValidation.PasswordLengthInvalid
        }

        val ageInt = signUpModel.age.toIntOrNull()

        if (ageInt == null) {
            return UserValidation.EmptyAge
        } else if (ageInt < MIN_AGE) {
            return UserValidation.InvalidAgeRange
        } else if (ageInt > MAX_AGE) {
            return UserValidation.InvalidAgeRange
        }

        return UserValidation.Valid
    }

    companion object {
        private const val MAX_AGE = 99
        private const val MIN_AGE = 18

        private const val PASSWORD_MIN_LENGTH = 6
        private const val PASSWORD_MAX_LENGTH = 12
    }

}