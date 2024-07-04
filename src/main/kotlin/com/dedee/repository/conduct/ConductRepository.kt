package com.dedee.repository.conduct

import com.dedee.model.conduct.ConductRequest
import com.dedee.model.conduct.ConductResponse
import util.ResponseFeedback


interface ConductRepository {
    suspend fun getAllConducts(): ResponseFeedback<List<ConductResponse>?>

    suspend fun addConduct(conduct: ConductRequest): ResponseFeedback<ConductResponse?>

    suspend fun getConduct(uniqueConductId: String): ResponseFeedback<ConductResponse?>

    suspend fun deleteConduct(uniqueConductId: String): ResponseFeedback<String>

    suspend fun updateConduct(uniqueConductId: String, updatedConduct: ConductRequest): ResponseFeedback<ConductResponse?>

    
}



