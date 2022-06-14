package com.laurentdarl.quicky.domain.usecases

data class ValidateResults(
    val isSuccessful: Boolean,
    val errorMessage: String? = null
)
