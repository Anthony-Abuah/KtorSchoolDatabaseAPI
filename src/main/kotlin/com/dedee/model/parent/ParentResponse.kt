package com.dedee.model.parent

import util.Constants

@kotlinx.serialization.Serializable
data class ParentResponse(
    val firstName: String,
    val lastName: String,
    val parentUsername: String,
    val password: String,
    val salt: String,
    val email: String,
    val contact: String,
    val gender: String,
    val hasAnEnrolledWard: Boolean?,
    val wards: String,
){
    fun toParentInfo(): ParentInfo{
        return ParentInfo(firstName, lastName,  parentUsername, email, contact, gender, hasAnEnrolledWard )
    }
}
