package com.android.auth.domain

interface PatternValidator {
    fun matcher(value: String) : Boolean
}