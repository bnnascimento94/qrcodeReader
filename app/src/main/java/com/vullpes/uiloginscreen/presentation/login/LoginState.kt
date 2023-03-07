package com.vullpes.uiloginscreen.presentation.login

data class LoginState(
    val isloading:Boolean = false,
    val buttonEnabled: Boolean = false,
    val usuario: String = "",
    val errorUsuario:String? = null,
    val senha:String = "",
    val errorSenha:String? = null
)