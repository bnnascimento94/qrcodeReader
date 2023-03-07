package com.vullpes.uiloginscreen.domain.validations

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = ""
)