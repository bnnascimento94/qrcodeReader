package com.vullpes.uiloginscreen.domain.validations

class ValidadeEmail {

    fun execute(email: String): ValidationResult{
        if(email.isBlank()){
            return ValidationResult(
                successful = false,
                errorMessage = "This field must not be empty"
            )
        }
        return ValidationResult(
            successful = true,
            errorMessage = null

        )
    }


}