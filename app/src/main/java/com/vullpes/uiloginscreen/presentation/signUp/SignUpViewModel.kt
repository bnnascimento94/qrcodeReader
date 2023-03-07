package com.vullpes.uiloginscreen.presentation.signUp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vullpes.uiloginscreen.data.util.Resource
import com.vullpes.uiloginscreen.domain.usecases.UserRegisterUseCase
import com.vullpes.uiloginscreen.domain.validations.ValidadeEmail
import com.vullpes.uiloginscreen.domain.validations.ValidateConfirmPassword
import com.vullpes.uiloginscreen.domain.validations.ValidatePassword
import com.vullpes.uiloginscreen.domain.validations.ValidateUsername
import com.vullpes.uiloginscreen.presentation.login.LoginViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val userRegisterUseCase: UserRegisterUseCase,
    private val validateEmail: ValidadeEmail,
    private val validatePassword: ValidatePassword,
    private val validateConfirmPassword: ValidateConfirmPassword,
    private val validateUsername: ValidateUsername
) : ViewModel() {

    var state by mutableStateOf(SignUpState())

    private val validationEventChannel = Channel<LoginViewModel.ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    fun onEvent(event: SignUpEvent) {
        when (event) {
            is SignUpEvent.UsuarioChanged -> {
                state = state.copy(
                    user = event.usuario,
                    userError = validateUsername.execute(event.usuario).errorMessage,
                    buttonEnabled = enabledSignUpButton()
                )
            }
            is SignUpEvent.EmailChanged -> {
                state = state.copy(
                    email = event.email,
                    emailError = validateEmail.execute(event.email).errorMessage,
                    buttonEnabled = enabledSignUpButton()
                )
            }
            is SignUpEvent.PasswordChanged -> {
                state = state.copy(
                    password = event.password,
                    passwordError = validatePassword.execute(event.password).errorMessage,
                    buttonEnabled = enabledSignUpButton()
                )
            }
            is SignUpEvent.ConfirmPasswordChanged -> {
                state = state.copy(
                    confirmPassword = event.confirmPassword,
                    confirmPasswordError = validateConfirmPassword.execute(state.password, state.confirmPassword).errorMessage,
                    buttonEnabled = enabledSignUpButton()
                )
            }
            is SignUpEvent.Submit -> {
                submitData()
            }
        }
    }


    private fun submitData() = viewModelScope.launch(Dispatchers.IO) {
        val result = userRegisterUseCase.registerNewUser(state.user, state.email,state.password)

        when(result){
            is Resource.Loading ->{
                state = state.copy(isLoading = true)
            }
            is Resource.Error ->{
                state = state.copy(isLoading = false)
                validationEventChannel.send(LoginViewModel.ValidationEvent.Error(result.message))
            }
            is Resource.Success ->{
                state = state.copy(
                    isLoading = false
                )

                validationEventChannel.send(LoginViewModel.ValidationEvent.Success)
            }
        }
    }


    fun enabledSignUpButton(): Boolean {
        return state.email != "" &&
                state.user != "" &&
                state.password != "" &&
                state.confirmPassword != ""
    }


    sealed class ValidationEvent {
        object Success : ValidationEvent()
        data class Error(val error: String?) : ValidationEvent()
    }
}