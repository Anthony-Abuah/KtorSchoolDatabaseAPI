package com.dedee.repository.school_class

import com.dedee.model.school_class.SchoolClassResponse
import com.dedee.model.school_class.SchoolClassRequest
import com.dedee.service.school_class.SchoolClassService
import util.Constants.emptyString
import util.DatabaseEntityFields.UniqueClassId
import util.Functions.uniqueClassIdGenerator
import util.Functions.uniqueSubjectIdGenerator
import util.ResponseFeedback

class SchoolClassRepositoryImpl(
    private val schoolClassService: SchoolClassService,
) : SchoolClassRepository{
    override suspend fun getAllSchoolClasses(): ResponseFeedback<List<SchoolClassResponse>?> {
        val schoolClassResponse = schoolClassService.getAllSchoolClasses()
        return if (schoolClassResponse.isNullOrEmpty()) {
            ResponseFeedback(
                data = null,
                message = "Could not fetch all Classes",
                success = false
            )
        }else{
            ResponseFeedback(
                data = schoolClassResponse,
                message = "All Classes successfully loaded",
                success = true
            )
        }
    }

    override suspend fun addSchoolClass(schoolClass: SchoolClassRequest): ResponseFeedback<SchoolClassResponse?> {
        val className = schoolClass.className
        val academicYear = schoolClass.academicYear
        val uniqueClassId = uniqueClassIdGenerator(className, academicYear)
        return if (uniqueSchoolClassIdExists(uniqueClassId)){
            ResponseFeedback(
                data = null,
                message = "Cannot add Class because it already exists",
                success = false
            )
        }else{
            val registerResponse = schoolClassService.addSchoolClass(schoolClass, uniqueClassId)
            if (registerResponse < 1 ) {
                ResponseFeedback(
                    data = null,
                    message = "Unable to add class",
                    success = false
                )
            }else{
                val registeredSchoolClass = schoolClassService.getSchoolClass(uniqueClassId, UniqueClassId)?.first()
                ResponseFeedback(
                    data = registeredSchoolClass,
                    message = "Class successfully added",
                    success = true
                )
            }
        }
    }

    override suspend fun getSchoolClass(
        parameterName: String,
        parameterType: String,
    ): ResponseFeedback<List<SchoolClassResponse>?> {
        val response = schoolClassService.getSchoolClass(parameterName, parameterType)
        return if (response.isNullOrEmpty()){
            ResponseFeedback(
                data = null,
                message = "No Classes matches the given criteria",
                success = false
            )
        }else{
            ResponseFeedback(
                data = response,
                message = "Classes fetched successfully",
                success = true
            )
        }
    }

    override suspend fun deleteSchoolClass(uniqueClassId: String): ResponseFeedback<String> {
        val response =  schoolClassService.deleteSchoolClass(uniqueClassId) ?: -1
        return if (response < 1){
            ResponseFeedback(
                data = "Unable to delete Class",
                message = "Unknown Error",
                success = false
            )
        }else{
            ResponseFeedback(
                data = "Class deleted successfully",
                message = "Class deleted successfully",
                success = true
            )
        }

    }

    override suspend fun updateSchoolClass(
        uniqueClassId: String,
        updatedSchoolClass: SchoolClassRequest,
    ): ResponseFeedback<SchoolClassResponse?> {
        val response = schoolClassService.updateSchoolClass(uniqueClassId, updatedSchoolClass)
        return if (response == null){
            ResponseFeedback(
                data = null,
                message = "Unable to update Class",
                success = false
            )
        }else{
            ResponseFeedback(
                data = response,
                message = "Class updated successfully",
                success = true
            )
        }
    }


    private suspend fun uniqueSchoolClassIdExists(uniqueSchoolClassId: String): Boolean {
        return !(schoolClassService.getSchoolClass(uniqueSchoolClassId, UniqueClassId).isNullOrEmpty())
    }

}