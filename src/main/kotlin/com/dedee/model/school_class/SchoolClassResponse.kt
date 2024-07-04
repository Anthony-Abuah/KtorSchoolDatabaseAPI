package com.dedee.model.school_class

@kotlinx.serialization.Serializable
data class SchoolClassResponse(
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
){
    fun toSchoolClassInfo(): SchoolClassInfo{
        return SchoolClassInfo(className, uniqueClassId, classStage, academicYear, numberOfBoys, numberOfGirls, totalNumberOfStudents, teachers, subjects, students)
    }
}
