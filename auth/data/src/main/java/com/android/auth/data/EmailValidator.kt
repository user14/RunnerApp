package com.android.auth.data

import android.util.Patterns
import com.android.auth.domain.PatternValidator

object EmailValidator : PatternValidator {

    override fun matcher(value: String): Boolean {
       return Patterns.EMAIL_ADDRESS.matcher(value).matches()
    }

}