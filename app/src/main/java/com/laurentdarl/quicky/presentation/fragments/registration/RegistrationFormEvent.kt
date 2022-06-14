package com.laurentdarl.quicky.presentation.fragments.registration

sealed class RegistrationFormEvent {
    data class NameChanged(val name: String): RegistrationFormEvent()
    data class PhoneChanged(val phone: String): RegistrationFormEvent()
    data class EmailChanged(val email: String): RegistrationFormEvent()
    data class PasswordChanged(val password: String): RegistrationFormEvent()
    data class ResetPasswordChanged(val repeatPassword: String): RegistrationFormEvent()

    object Submit: RegistrationFormEvent()
}
