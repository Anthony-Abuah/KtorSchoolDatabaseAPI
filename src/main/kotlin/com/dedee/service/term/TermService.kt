package com.dedee.service.term

import com.dedee.model.term.TermRequest
import com.dedee.model.term.TermResponse

interface TermService {

    suspend fun getAllTerms(): List<TermResponse>?

    suspend fun addTerm(term: TermRequest, uniqueTermName: String): Int

    suspend fun getTerm(parameterName: String, parameterType: String): List<TermResponse>?

    suspend fun updateTerm(uniqueTermName: String, updatedTerm: TermRequest): TermResponse?

    suspend fun deleteTerm(uniqueTermName: String): Int?

}