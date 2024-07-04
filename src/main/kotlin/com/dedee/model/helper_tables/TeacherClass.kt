package com.dedee.model.helper_tables

@kotlinx.serialization.Serializable
data class TeacherClass(
    val uniqueTeacherId: String,
    val uniqueClassId: String
)
