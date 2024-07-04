package com.dedee.repository.school_class

import com.dedee.model.school_class.SchoolClassRequest
import com.dedee.model.school_class.SchoolClassResponse
import util.ResponseFeedback


interface SchoolClassRepository {
    suspend fun getAllSchoolClasses(): ResponseFeedback<List<SchoolClassResponse>?>

    suspend fun addSchoolClass(schoolClass: SchoolClassRequest): ResponseFeedback<SchoolClassResponse?>

    suspend fun getSchoolClass(parameterName: String, parameterType: String): ResponseFeedback<List<SchoolClassResponse>?>

    suspend fun deleteSchoolClass(uniqueClassId: String): ResponseFeedback<String>

    suspend fun updateSchoolClass(uniqueClassId: String, updatedSchoolClass: SchoolClassRequest): ResponseFeedback<SchoolClassResponse?>

    
}



