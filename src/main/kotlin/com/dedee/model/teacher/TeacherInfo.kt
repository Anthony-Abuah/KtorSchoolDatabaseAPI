package com.dedee.model.teacher

data class TeacherInfo(
    val firstName: String?,
    val lastName: String?,
    val otherNames: String?,
    val teacherUsername: String?,
    val uniqueTeacherId: String?,
    val email: String?,
    val contact: String?,
    val gender: String?,
    val isStillAtThisSchool: Boolean?
)
