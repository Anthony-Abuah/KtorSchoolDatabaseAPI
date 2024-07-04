package com.dedee.service.grading

import com.dedee.model.grading.GradingRequest
import com.dedee.model.grading.GradingResponse

interface GradingService {

    suspend fun getAllGradings(): List<GradingResponse>?

    suspend fun addScore(scores: String, uniqueGradingId: String)

    suspend fun addGrading(grading: GradingRequest, uniqueGradingId: String): Int

    suspend fun getGrading(uniqueGradingId: String): GradingResponse?

    suspend fun getClassGradings(uniqueClassId: String, uniqueSubjectId: String, uniqueTermName: String): List<GradingResponse>?

    suspend fun getStudentGradings(uniqueStudentId: String, uniqueClassId: String, uniqueSubjectId: String?, uniqueTermName: String? ): List<GradingResponse>?

    suspend fun updateGrading(uniqueGradingId: String, updatedGrading: GradingRequest): GradingResponse?

    suspend fun deleteGrading(uniqueGradingId: String): Int?

}