package com.vullpes.uiloginscreen.domain.validations

import com.google.mlkit.common.sdkinternal.model.ModelValidator

class ValidatePassword {

    fun execute(password: String): ValidationResult{
        if(password.length < 8) {
            return ValidationResult(
                successful = false,
                errorMessage = "at least 8 characters"
            )
        }
        val containsLettersAndDigits = password.any { it.isDigit() } &&
                password.any { it.isLetter() }
        if(!containsLettersAndDigits) {
            return ValidationResult(
                successful = false,
                errorMessage = "at least one letter and digit"
            )
        }
        return ValidationResult(
            successful = true,
            errorMessage = null
        )
    }
}