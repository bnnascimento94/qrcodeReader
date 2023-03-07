package com.vullpes.uiloginscreen.presentation.login

import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vullpes.uiloginscreen.data.util.Resource
import com.vullpes.uiloginscreen.domain.usecases.DefaultUserUseCase
import com.vullpes.uiloginscreen.domain.usecases.LogInUseCase
import com.vullpes.uiloginscreen.domain.validations.ValidadeEmail
import com.vullpes.uiloginscreen.domain.validations.ValidatePassword
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val logInUseCase: LogInUseCase,
    private val defaultUserUseCase: DefaultUserUseCase,
    private val validadeEmail: ValidadeEmail,
    private val validatePassword: ValidatePassword
): ViewModel() {

    init {
        insertDefaultUser()
    }

    var state by mutableStateOf(LoginState())

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    fun onEvent(event: LoginFormEvent) {
        when (event) {
            is LoginFormEvent.EmailChanged -> {


                state = state.copy(
                    usuario = event.email,
                    errorUsuario = validadeEmail.execute(event.email).errorMessage,
                    buttonEnabled = enabledLoginButton()
                )

            }
            is LoginFormEvent.PasswordChanged -> {
                state = state.copy(
                    senha = event.password,
                    errorSenha = validatePassword.execute(event.password).errorMessage,
                    buttonEnabled = enabledLoginButton()
                )
            }
            is LoginFormEvent.Submit -> {
                submitData()
            }
        }
    }


    fun enabledLoginButton(): Boolean {
        return state.usuario != "" &&
                state.senha != "" &&
                state.errorSenha == null &&
                state.errorUsuario == null
    }

    private fun insertDefaultUser() = viewModelScope.launch(Dispatchers.IO) {
        defaultUserUseCase.execute()
    }

    private fun submitData() = viewModelScope.launch(Dispatchers.IO) {
       val result = logInUseCase.execute(state.usuario, state.senha)

        when(result){
            is Resource.Loading ->{
                state = state.copy(
                    isloading = true
                )
            }
            is Resource.Error ->{
                state = state.copy(
                    isloading = false
                )
                validationEventChannel.send(ValidationEvent.Error(result.message))
            }
            is Resource.Success ->{
                state = state.copy(
                    isloading = false
                )

                validationEventChannel.send(ValidationEvent.Success)
            }
        }
    }

    sealed class ValidationEvent {
        object Success: ValidationEvent()
        data class Error(val error: String?):ValidationEvent()
    }


}