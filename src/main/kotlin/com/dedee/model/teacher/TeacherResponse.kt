package com.dedee.model.teacher

@kotlinx.serialization.Serializable
data class TeacherResponse(
    val firstName: String,
    val lastName: String,
    val otherNames: String?,
    val teacherUsername: String,
    val uniqueTeacherId: String,
    val password: String,
    val salt: String,
    val email: String,
    val contact: String,
    val gender: String,
    val isStillAtThisSchool: Boolean?,
    val subjects: String,
    val classes: String?,
    val students: String?,
){
    fun toTeacherInfo(): TeacherInfo{
        return TeacherInfo(firstName, lastName, otherNames, teacherUsername, uniqueTeacherId, email, contact, gender, isStillAtThisSchool)
    }
}
