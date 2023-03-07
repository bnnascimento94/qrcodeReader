package com.vullpes.uiloginscreen.presentation.signUp

import com.vullpes.uiloginscreen.presentation.login.LoginFormEvent

sealed class SignUpEvent {
    data class UsuarioChanged(val usuario:String): SignUpEvent()
    data class EmailChanged(val email:String): SignUpEvent()
    data class PasswordChanged(val password:String): SignUpEvent()
    data class ConfirmPasswordChanged(val confirmPassword: String): SignUpEvent()
    object Submit: SignUpEvent()
}