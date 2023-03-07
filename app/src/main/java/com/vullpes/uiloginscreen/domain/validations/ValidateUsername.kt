package com.vullpes.uiloginscreen.domain.validations

class ValidateUsername {

    fun execute(username: String): ValidationResult{
        if(username.isBlank()){
            return ValidationResult(successful = false, errorMessage = "User field must not be empty")
        }

        return ValidationResult(successful = true, errorMessage = null)
    }
}