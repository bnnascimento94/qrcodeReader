package com.vullpes.uiloginscreen.presentation

sealed class Routes(val route:String) {
    object Login:Routes("Login")
    object SignUpScreen:Routes("SignUp")
    object QrcodeScreen:Routes("QrcodeScreen")
    object ListQrcodeScreen: Routes("ListQrcodeScreen")
}