package com.dedee.security.hashing

@kotlinx.serialization.Serializable
data class SaltedHash (
    val hash: String,
    val salt: String
)