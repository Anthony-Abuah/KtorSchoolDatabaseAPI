package com.dedee.model

@kotlinx.serialization.Serializable
data class Notes (
    val id: Int,
    val note: String
)