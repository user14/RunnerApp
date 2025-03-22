package com.android.auth.presentation.resigter

sealed interface RegisterAction {
    data object OnTogglePasswordVisibilityClick : RegisterAction
    data object OnLoginClick : RegisterAction
    data object OnRegisterClick : RegisterAction
}