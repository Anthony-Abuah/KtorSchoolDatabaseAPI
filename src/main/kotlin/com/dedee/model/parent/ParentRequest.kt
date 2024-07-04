package com.dedee.model.parent

import com.dedee.model.helper_tables.Item
import util.Constants.emptyString

@kotlinx.serialization.Serializable
data class ParentRequest(
    val firstName: String?,
    val lastName: String?,
    val password: String,
    val email: String,
    val contact: String,
    val gender: String,
)
