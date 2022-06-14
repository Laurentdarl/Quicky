package com.laurentdarl.quicky.data.registration.models

data class RegistrationState(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    var phone: String = "",
    val emailError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
    val repeatPassword: String = "",
    val repeatPasswordError: String? = null
)
