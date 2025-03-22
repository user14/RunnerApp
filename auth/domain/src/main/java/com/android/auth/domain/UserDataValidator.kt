package com.android.auth.domain

class UserDataValidator(
    private val patternValidator: PatternValidator
) {

    fun isEmailValid(email: String):Boolean{
        return patternValidator.matcher(email.trim())
    }

    fun validatePassword(password :String) : PasswordValidationState{
        val hasMinLength = password.length >= PASSWORD_MIN_LENGTH
        val hasUppercase = password.any { it.isUpperCase() }
        val hasLowercase = password.any { it.isLowerCase() }
        val hasDigit = password.any { it.isDigit() }

        return PasswordValidationState(
            hasMinLength = hasMinLength,
            hasUpperCase = hasUppercase,
            hasLowerCase = hasLowercase,
            hasNumber = hasDigit
        )
    }

    companion object{
        const val PASSWORD_MIN_LENGTH = 8
    }

}