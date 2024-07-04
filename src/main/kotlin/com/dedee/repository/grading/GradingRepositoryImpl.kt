package com.dedee.repository.grading

import com.dedee.model.grading.GradingInfo
import com.dedee.model.grading.GradingRequest
import com.dedee.model.grading.GradingResponse
import com.dedee.service.grading.GradingService
import com.dedee.service.student.StudentService
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import util.Constants
import util.DatabaseEntityFields.SubjectName
import util.DatabaseEntityFields.UniqueStudentId
import util.Functions.uniqueGradingIdGenerator
import util.ResponseFeedback

class GradingRepositoryImpl(
    private val gradingService: GradingService,
    private val studentService: StudentService,
    private val gson: Gson,
) : GradingRepository{
    override suspend fun getAllGradings(): ResponseFeedback<List<GradingResponse>?> {
        val gradingResponse = gradingService.getAllGradings()
        return if (gradingResponse.isNullOrEmpty()) {
            ResponseFeedback(
                data = null,
                message = "Could not fetch all gradings",
                success = false
            )
        }else{
            ResponseFeedback(
                data = gradingResponse,
                message = "All gradings successfully loaded",
                success = true
            )
        }
    }

    override suspend fun addGrading(grading: GradingRequest): ResponseFeedback<GradingResponse?> {
        val uniqueSubjectId = grading.uniqueSubjectId ?: SubjectName
        val uniqueStudentId = grading.uniqueStudentId ?: UniqueStudentId
        val uniqueGradingId = uniqueGradingIdGenerator(uniqueStudentId, uniqueSubjectId)
        return if (uniqueGradingIdExists(uniqueGradingId)){
            ResponseFeedback(
                data = null,
                message = "Cannot add grading because it already exists",
                success = false
            )
        }else{
            val registerResponse = gradingService.addGrading(grading, uniqueGradingId)
            if (registerResponse < 1 ) {
                ResponseFeedback(
                    data = null,
                    message = "Unable to add grading",
                    success = false
                )
            }else{
                val thisGrading = gradingService.getGrading(uniqueGradingId)
                val student = uniqueStudentId.let { studentService.getStudents(it, UniqueStudentId)?.firstOrNull() }
                val gradingJSON = student?.gradings
                val listOfGradingInfoType = object : TypeToken<List<GradingInfo>>() {}.type
                val gradingInfoList: List<GradingInfo> = gson.fromJson(gradingJSON, listOfGradingInfoType)
                val mutableListOfGradingInfo = gradingInfoList.toMutableList()
                thisGrading?.toGradingInfo()?.let { mutableListOfGradingInfo.add(it) }
                val updatedGradingListJSON = gson.toJson(mutableListOfGradingInfo)
                studentService.addGradings(updatedGradingListJSON, uniqueStudentId)

                val registeredGrading = gradingService.getGrading(uniqueGradingId)
                ResponseFeedback(
                    data = registeredGrading,
                    message = "Grading successfully added",
                    success = true
                )
            }
        }
    }

    override suspend fun getGrading(uniqueGradingId: String): ResponseFeedback<GradingResponse?> {
        val gradingResponse = gradingService.getGrading(uniqueGradingId)
        return if (gradingResponse == null) {
            ResponseFeedback(
                data = null,
                message = "Could not fetch all gradings",
                success = false
            )
        }else{
            ResponseFeedback(
                data = gradingResponse,
                message = "Requested grading successfully loaded",
                success = true
            )
        }
    }

    override suspend fun getClassGradings(
        uniqueClassId: String,
        uniqueSubjectId: String,
        uniqueTermName: String,
    ): ResponseFeedback<List<GradingResponse>?> {
        val gradingResponse = gradingService.getClassGradings(uniqueClassId, uniqueSubjectId, uniqueTermName)
        return if (gradingResponse.isNullOrEmpty()) {
            ResponseFeedback(
                data = null,
                message = "Could not fetch all gradings",
                success = false
            )
        }else{
            ResponseFeedback(
                data = gradingResponse,
                message = "Requested gradings successfully loaded",
                success = true
            )
        }
    }

    override suspend fun getStudentGradings(
        uniqueStudentId: String,
        uniqueClassId: String,
        uniqueSubjectId: String?,
        uniqueTermName: String?,
    ): ResponseFeedback<List<GradingResponse>?> {
        val gradingResponse = gradingService.getStudentGradings(uniqueStudentId, uniqueClassId, uniqueSubjectId, uniqueTermName)
        return if (gradingResponse.isNullOrEmpty()) {
            ResponseFeedback(
                data = null,
                message = "Could not fetch requested gradings",
                success = false
            )
        }else{
            ResponseFeedback(
                data = gradingResponse,
                message = "Requested gradings successfully loaded",
                success = true
            )
        }
    }

    override suspend fun deleteGrading(uniqueGradingId: String): ResponseFeedback<String> {
        val response =  gradingService.deleteGrading(uniqueGradingId) ?: -1
        return if (response < 1){
            ResponseFeedback(
                data = "Unable to delete grading",
                message = "Unknown Error",
                success = false
            )
        }else{
            ResponseFeedback(
                data = "Grading deleted successfully",
                message = "Grading deleted successfully",
                success = true
            )
        }

    }

    override suspend fun updateGrading(
        uniqueGradingId: String,
        updatedGrading: GradingRequest,
    ): ResponseFeedback<GradingResponse?> {
        val thisGrading = gradingService.updateGrading(uniqueGradingId, updatedGrading)
        return if (thisGrading == null){
            ResponseFeedback(
                data = null,
                message = "Unable to update grading",
                success = false
            )
        }else{
            val uniqueStudentId = thisGrading.uniqueStudentId ?: Constants.emptyString
            val student = uniqueStudentId.let { studentService.getStudents(it, UniqueStudentId)?.firstOrNull() }
            val gradingJSON = student?.gradings
            val listOfGradingInfoType = object : TypeToken<List<GradingInfo>>() {}.type
            val gradingInfoList: List<GradingInfo> = gson.fromJson(gradingJSON, listOfGradingInfoType)
            val mutableListOfGradingInfo = gradingInfoList.toMutableList()
            mutableListOfGradingInfo.removeIf { it.uniqueGradingId == uniqueGradingId }
            thisGrading.toGradingInfo().let { mutableListOfGradingInfo.add(it) }
            val updatedGradingListJSON = gson.toJson(mutableListOfGradingInfo)
            studentService.addGradings(updatedGradingListJSON, uniqueStudentId)
            ResponseFeedback(
                data = thisGrading,
                message = "Grading updated successfully",
                success = true
            )
        }
    }


    private suspend fun uniqueGradingIdExists(uniqueGradingId: String): Boolean {
        return gradingService.getGrading(uniqueGradingId) != null
    }

}