package com.vullpes.uiloginscreen.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vullpes.uiloginscreen.presentation.listQrcode.ListQrcodeScreen
import com.vullpes.uiloginscreen.presentation.login.LoginScreen
import com.vullpes.uiloginscreen.presentation.qrcodeScreen.QrcodeScreen
import com.vullpes.uiloginscreen.presentation.signUp.SignUpScreen

@Composable
fun MainScreen() {
    val navControler = rememberNavController()
    NavHost(navController = navControler, startDestination = Routes.Login.route){
        composable(Routes.Login.route){
            LoginScreen(navControler)
        }

        composable(Routes.SignUpScreen.route){
            SignUpScreen(navControler)
        }

        composable(Routes.QrcodeScreen.route){
            QrcodeScreen(navControler)
        }

        composable(Routes.ListQrcodeScreen.route){
            ListQrcodeScreen(navControler = navControler)
        }

    }
}
