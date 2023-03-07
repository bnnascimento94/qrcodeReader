package com.vullpes.uiloginscreen.domain.validations

class ValidateConfirmPassword {

    fun execute(password: String, confirmPassword: String): ValidationResult{
        if(password.isBlank()){
            return ValidationResult(
                successful = false,
                errorMessage = "Write password first"
            )
        }

        if(!password.equals(confirmPassword)){
            return ValidationResult(
                successful = false,
                errorMessage = "Confirmation doesn't match"
            )
        }

        return ValidationResult(
            successful = true,
            errorMessage = null
        )
    }
}