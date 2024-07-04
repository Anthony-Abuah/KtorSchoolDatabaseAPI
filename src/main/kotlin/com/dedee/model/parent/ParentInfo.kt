package com.dedee.model.parent

@kotlinx.serialization.Serializable
data class ParentInfo(
    val firstName: String,
    val lastName: String,
    val parentUsername: String,
    val email: String,
    val contact: String,
    val gender: String,
    val hasAnEnrolledWard: Boolean?,
)
