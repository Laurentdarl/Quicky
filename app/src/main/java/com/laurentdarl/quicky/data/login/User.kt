package com.laurentdarl.quicky.data.login

data class User(
    val userName: String? = null,
    val email: String? = null,
    var phone: String? = null,
    val password: String? = null,
    val uid: String? = null,
    val repeatPassword: String? = null
)
