package com.dedee.model.school_class

@kotlinx.serialization.Serializable
data class SchoolClassRequest(
    val className: String,
    val academicYear: String,
    val classStage: String?,
    val numberOfBoys: Int?,
    val numberOfGirls: Int?,
    val totalNumberOfStudents: Int?,
    val teachers: String?,
    val subjects: String?,
    val students: String?,
)
