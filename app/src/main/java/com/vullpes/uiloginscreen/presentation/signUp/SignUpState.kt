package com.vullpes.uiloginscreen.presentation.signUp

data class SignUpState (
    val isLoading: Boolean = false,
    val buttonEnabled: Boolean = false,
    val user:String = "",
    val userError: String? = null,
    val email:String = "",
    val emailError:String? = null,
    val password: String = "",
    val passwordError: String? = null,
    val confirmPassword: String = "",
    val confirmPasswordError:String? = null

)