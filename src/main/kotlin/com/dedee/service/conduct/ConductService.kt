package com.dedee.service.conduct

import com.dedee.model.conduct.ConductRequest
import com.dedee.model.conduct.ConductResponse

interface ConductService {

    suspend fun getAllConducts(): List<ConductResponse>?

    suspend fun addConduct(conduct: ConductRequest, uniqueConductId: String): Int

    suspend fun getConduct(uniqueConductId: String): ConductResponse?

    suspend fun updateConduct(uniqueConductId: String, updatedConduct: ConductRequest): ConductResponse?

    suspend fun deleteConduct(uniqueConductId: String): Int?

}