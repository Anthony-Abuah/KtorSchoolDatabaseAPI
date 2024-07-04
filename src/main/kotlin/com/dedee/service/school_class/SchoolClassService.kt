package com.dedee.service.school_class

import com.dedee.model.school_class.SchoolClassRequest
import com.dedee.model.school_class.SchoolClassResponse

interface SchoolClassService {

    suspend fun getAllSchoolClasses(): List<SchoolClassResponse>?

    suspend fun addSubjects(subjects: String, uniqueClassId: String)

    suspend fun addTeachers(teachers: String, uniqueClassId: String)

    suspend fun addSchoolClass(schoolClass: SchoolClassRequest, uniqueClassId: String): Int

    suspend fun getSchoolClass(parameterName: String, parameterType: String): List<SchoolClassResponse>?

    suspend fun updateSchoolClass(uniqueClassId: String, updatedClass: SchoolClassRequest): SchoolClassResponse?

    suspend fun deleteSchoolClass(uniqueClassId: String): Int?

}