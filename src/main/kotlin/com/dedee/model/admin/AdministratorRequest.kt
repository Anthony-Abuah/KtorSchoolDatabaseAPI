package com.dedee.model.admin

@kotlinx.serialization.Serializable
data class AdministratorRequest(
    val firstName: String,
    val lastName: String,
    val password: String,
    val email: String,
    val position: String,
    val gender: String,
)
