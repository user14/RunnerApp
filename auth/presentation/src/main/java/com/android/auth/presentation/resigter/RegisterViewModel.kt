@file:Suppress("OPT_IN_USAGE_FUTURE_ERROR")
@file:OptIn(ExperimentalFoundationApi::class)

package com.android.auth.presentation.resigter

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.input.textAsFlow
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.auth.domain.AuthRepository
import com.android.auth.domain.UserDataValidator
import com.android.auth.presentation.R
import com.android.core.domain.util.DataError
import com.android.core.domain.util.Result
import com.android.core.presentation.ui.UiText
import com.android.core.presentation.ui.asUiText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val userDataValidator: UserDataValidator,
    private val authRepository: AuthRepository
) : ViewModel() {
    var  state by mutableStateOf(RegisterState())
        private set

    private val eventChannel = Channel<RegisterEvent> ()
    val events = eventChannel.receiveAsFlow()

    init {
        state.email.textAsFlow()
            .onEach { email ->
                val isValidEmail = userDataValidator.isEmailValid(email.toString())
                state = state.copy(
                    isEmailValid = isValidEmail,
                    canRegister = isValidEmail && state.passwordValidState.isValidPassword
                            && !state.isRegistering
                )
            }
            .launchIn(viewModelScope)

        state.password.textAsFlow()
            .onEach { password ->
                val passwordValidationState = userDataValidator.validatePassword(password.toString())
                state = state.copy(
                    passwordValidState = passwordValidationState,
                    canRegister = state.isEmailValid && passwordValidationState.isValidPassword
                            && !state.isRegistering
                )
            }
            .launchIn(viewModelScope)
    }
    fun onAction(event : RegisterAction){
        when(event){
            RegisterAction.OnRegisterClick -> register()
            RegisterAction.OnTogglePasswordVisibilityClick -> {
                state = state.copy(isPasswordVisible = !state.isPasswordVisible)
            }
            else -> Unit
        }

    }

    private fun register(){
        viewModelScope.launch {
            state = state.copy(
                isRegistering = true
            )

            val result = authRepository.register(
                state.email.text.toString().trim(),
                state.password.text.toString()
            )
            state = state.copy(isRegistering = false)
            when(result){
                is Result.Error -> {
                  /*  if(result.error == DataError.Network.CONFLICT){
                        eventChannel.send(RegisterEvent.Error(
                            UiText.StringResource(R.string.error_email_exists)
                        ))
                    }
                    else*/
                    eventChannel.send(RegisterEvent.Error(result.error.asUiText()))

                }
                is Result.Success -> eventChannel.send(RegisterEvent.RegistrationSuccess)
            }
        }
    }

}