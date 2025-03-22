package com.android.auth.domain

data class PasswordValidationState(
    val hasMinLength: Boolean = false,
    val hasLowerCase: Boolean = false,
    val hasUpperCase: Boolean = false,
    val hasNumber: Boolean = false
) {
    val isValidPassword: Boolean
        get() = hasMinLength && hasLowerCase && hasUpperCase && hasNumber
}
