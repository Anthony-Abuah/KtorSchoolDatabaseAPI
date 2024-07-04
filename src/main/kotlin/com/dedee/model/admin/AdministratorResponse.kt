package com.dedee.model.admin

@kotlinx.serialization.Serializable
data class AdministratorResponse(
    val administratorId: Int,
    val firstName: String?,
    val lastName: String?,
    val adminUsername: String,
    val password: String,
    val salt: String,
    val email: String,
    val position: String,
    val gender: String,
)
