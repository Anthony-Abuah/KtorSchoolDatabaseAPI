package com.dedee.repository.grading

import com.dedee.model.grading.GradingRequest
import com.dedee.model.grading.GradingResponse
import util.ResponseFeedback


interface GradingRepository {
    suspend fun getAllGradings(): ResponseFeedback<List<GradingResponse>?>

    suspend fun addGrading(grading: GradingRequest): ResponseFeedback<GradingResponse?>

    suspend fun getGrading(uniqueGradingId: String): ResponseFeedback<GradingResponse?>

    suspend fun getClassGradings(uniqueClassId: String, uniqueSubjectId: String, uniqueTermName: String): ResponseFeedback<List<GradingResponse>?>

    suspend fun getStudentGradings(uniqueStudentId: String, uniqueClassId: String, uniqueSubjectId: String?, uniqueTermName: String? ): ResponseFeedback<List<GradingResponse>?>

    suspend fun deleteGrading(uniqueGradingId: String): ResponseFeedback<String>

    suspend fun updateGrading(uniqueGradingId: String, updatedGrading: GradingRequest): ResponseFeedback<GradingResponse?>

    
}



