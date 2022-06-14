package com.laurentdarl.quicky.domain.usecases

import android.util.Patterns

class ValidateEmail {
    fun execute(email: String): ValidateResults {
        if (email.isBlank()) {
            return ValidateResults(
                isSuccessful = false,
                errorMessage = "Email field is compulsory"
            )
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return ValidateResults(
                isSuccessful = false,
                errorMessage = "Please input a valid email address"
            )
        }
        return ValidateResults(
            isSuccessful = true
        )
    }
}