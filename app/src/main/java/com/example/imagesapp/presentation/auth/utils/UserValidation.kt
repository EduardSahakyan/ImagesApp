package com.example.imagesapp.presentation.auth.utils

enum class UserValidation {
    Valid,
    EmptyEmail,
    InvalidEmailFormat,
    EmptyPassword,
    PasswordLengthInvalid,
    EmptyAge,
    InvalidAgeRange
}