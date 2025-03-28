package com.android.auth.presentation.resigter

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.auth.domain.PasswordValidationState
import com.android.auth.presentation.R
import com.android.core.presentation.designsystem.CheckIcon
import com.android.core.presentation.designsystem.CrossIcon
import com.android.core.presentation.designsystem.EmailIcon
import com.android.core.presentation.designsystem.Poppins
import com.android.core.presentation.designsystem.RunnerDarkRed
import com.android.core.presentation.designsystem.RunnerGray
import com.android.core.presentation.designsystem.RunnerGreen
import com.android.core.presentation.designsystem.RunnerTheme
import com.android.core.presentation.designsystem.components.GradientBackground
import com.android.core.presentation.designsystem.components.RunnerActionButton
import com.android.core.presentation.designsystem.components.RunnerPasswordTextField
import com.android.core.presentation.designsystem.components.RunnerTextField
import com.android.core.presentation.ui.ObserveAsEvent
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegisterScreenRoot(
    onSignInClick: () -> Unit,
    onSuccessfulRegistration: () -> Unit,
    viewModel: RegisterViewModel = koinViewModel(),
) {

    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    ObserveAsEvent(viewModel.events) { event ->
        when(event){
            is RegisterEvent.Error -> {
                keyboardController?.hide()
                Toast.makeText(
                    context,
                    event.error.asString(context),
                    Toast.LENGTH_LONG
                ).show()
            }
            is RegisterEvent.RegistrationSuccess -> {

                Toast.makeText(
                    context,
                   R.string.registration_successful,
                    Toast.LENGTH_LONG
                ).show()

                onSuccessfulRegistration()
            }
        }
    }


    RegisterScreen(
        state = viewModel.state,
        onAction = viewModel::onAction
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun RegisterScreen(
    state: RegisterState,
    onAction: (RegisterAction) -> Unit,
) {
    GradientBackground {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(vertical = 32.dp)
                .padding(top = 16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.create_account),
                style = MaterialTheme.typography.headlineMedium
            )

            val annotatedString = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontFamily = Poppins,
                        color = RunnerGray
                    )
                )
                {
                    append(stringResource(id = R.string.already_have_an_account) + " ")
                    pushStringAnnotation(
                        tag = "clickable_text",
                        annotation = stringResource(id = R.string.login)
                    )
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.primary,
                            fontFamily = Poppins
                        )
                    )
                    {
                        append(stringResource(id = R.string.login))
                    }
                }
            }

            ClickableText(
                text = annotatedString,
                onClick = { offset ->
                    annotatedString.getStringAnnotations(
                        tag = "clickable_text",
                        start = offset,
                        end = offset
                    ).firstOrNull()?.let {
                        onAction(RegisterAction.OnLoginClick)
                    }
                }
            )
            Spacer(modifier = Modifier.height(48.dp))
            RunnerTextField(
                state = state.email,
                startIcon = EmailIcon,
                endIcon = if (state.isEmailValid)
                    CheckIcon else null,
                hints = stringResource(id = R.string.example_email),
                title = stringResource(id = R.string.email),
                modifier = Modifier.fillMaxWidth(),
                additionalInfo = stringResource(id = R.string.must_be_valid_email),
                keyboardType = KeyboardType.Email
            )

            Spacer(modifier = Modifier.height(12.dp))
            RunnerPasswordTextField(
                state = state.password,
                hints = stringResource(id = R.string.password),
                title = stringResource(id = R.string.password),
                isPasswordVisible = state.isPasswordVisible,
                modifier = Modifier.fillMaxWidth(),
                keyboardType = KeyboardType.Password,
                onTogglePasswordVisibility = {
                    onAction(RegisterAction.OnTogglePasswordVisibilityClick)
                }
            )
            Spacer(modifier = Modifier.height(4.dp))
            PasswordRequirement(
                isValid = state.passwordValidState.hasMinLength,
                text = stringResource(id = R.string.at_least_x_characters, 8)
            )
            Spacer(modifier = Modifier.height(4.dp))
            PasswordRequirement(
                isValid = state.passwordValidState.hasNumber,
                text = stringResource(id = R.string.at_least_one_number)
            )
            Spacer(modifier = Modifier.height(4.dp))
            PasswordRequirement(
                isValid = state.passwordValidState.hasLowerCase,
                text = stringResource(id = R.string.contains_lowercase_char)
            )
            Spacer(modifier = Modifier.height(4.dp))
            PasswordRequirement(
                isValid = state.passwordValidState.hasUpperCase,
                text = stringResource(id = R.string.contains_uppercase_char)
            )
            Spacer(modifier = Modifier.height(34.dp))
            RunnerActionButton(text = stringResource(id = R.string.register),
                isLoading = state.isRegistering,
                enabled = state.canRegister,
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    onAction(RegisterAction.OnRegisterClick)
                }
            )
        }
    }

}

@Composable
fun PasswordRequirement(
    isValid: Boolean,
    text: String,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = if (isValid)
                CheckIcon
            else
                CrossIcon, contentDescription = null,
            tint = if (isValid)
                RunnerGreen
            else
                RunnerDarkRed
        )
        Spacer(modifier = Modifier.width(14.dp))
        Text(
            text = text, color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 14.sp
        )
    }
}

@Preview
@Composable
private fun RegisterScreenPreview() {
    RunnerTheme {
        RegisterScreen(
            state = RegisterState(
                passwordValidState = PasswordValidationState(
                    hasNumber = true,
                    hasLowerCase = true,
                    hasMinLength = true,
                    hasUpperCase = true
                ),
                isRegistering = true
            ),
            onAction = {}
        )
    }
}