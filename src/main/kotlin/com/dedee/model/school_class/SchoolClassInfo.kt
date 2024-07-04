package com.dedee.model.school_class

@kotlinx.serialization.Serializable
data class SchoolClassInfo(
    val className: String?,
    val uniqueClassId: String?,
    val classStage: String?,
    val academicYear: String?,
    val numberOfBoys: Int,
    val numberOfGirls: Int?,
    val totalNumberOfStudents: Int?,
    val teachers: String?,
    val subjects: String?,
    val students: String?,
)
