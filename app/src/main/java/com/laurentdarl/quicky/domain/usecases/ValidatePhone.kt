package com.laurentdarl.quicky.domain.usecases

class ValidatePhone {
    fun execute(phone: String): ValidateResults {
        if (phone.isBlank()) {
            return ValidateResults(
                isSuccessful = false,
                errorMessage = "Phone number field is compulsory"
            )
        }

        if (phone.length < 11) {
            return ValidateResults(
                isSuccessful = false,
                errorMessage = "Phone number length is incomplete"
            )
        }

        val phoneCheck = phone.any { it.isDigit() }
        if (!phoneCheck) {
            return ValidateResults(
                isSuccessful = false,
                errorMessage = "Please input a valid phone number"
            )
        }
        return ValidateResults(
            isSuccessful = true
        )
    }
}