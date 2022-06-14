package com.laurentdarl.quicky.domain.usecases

import android.util.Patterns
import java.util.regex.Pattern

class ValidateRepeatPassword {
    fun execute(password: String, repeatPassword: String): ValidateResults {
        if (password != repeatPassword) {
            return ValidateResults(
                isSuccessful = false,
                errorMessage = "Password don't match"
            )
        }
        return ValidateResults(
            isSuccessful = true
        )
    }
}