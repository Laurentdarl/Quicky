package com.laurentdarl.quicky.domain.usecases

import android.util.Patterns
import java.util.regex.Pattern

class ValidatePassword {
    fun execute(password: String): ValidateResults {
        if (password.isBlank()) {
            return ValidateResults(
                isSuccessful = false,
                errorMessage = "Password field is compulsory"
            )
        }
        if (password.length < 8) {
            return ValidateResults(
                isSuccessful = false,
                errorMessage = "Password length should be at least 8 characters"
            )
        }
        val passwordStrength = password.any { it.isDigit() } &&
                password.any { it.isLetter() } && !password.contains(Regex("[^a-zA-Z0-9 ]"))
        if (!passwordStrength) {
            return ValidateResults(
                isSuccessful = false,
                errorMessage = "Your password must contain at least one" +
                        " letter, number and special character"
            )
        }
        return ValidateResults(
            isSuccessful = true
        )
    }
}