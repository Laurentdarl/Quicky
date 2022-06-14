package com.laurentdarl.quicky.domain.usecases

import android.util.Patterns
import java.util.regex.Pattern

class ValidateName {
    fun execute(name: String): ValidateResults {
        if (name.isBlank()) {
            return ValidateResults(
                isSuccessful = false,
                errorMessage = "Name field is compulsory"
            )
        }

        return ValidateResults(
            isSuccessful = true
        )
    }
}