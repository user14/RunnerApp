package com.android.auth.presentation.resigter

import com.android.core.presentation.ui.UiText

sealed interface RegisterEvent {
    data object RegistrationSuccess : RegisterEvent
    data class Error(val error : UiText) : RegisterEvent

}