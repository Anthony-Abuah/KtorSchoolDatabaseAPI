package com.dedee.repository.term

import com.dedee.model.term.TermRequest
import com.dedee.model.term.TermResponse
import util.ResponseFeedback


interface TermRepository {
    suspend fun getAllTerms(): ResponseFeedback<List<TermResponse>?>

    suspend fun addTerm(term: TermRequest): ResponseFeedback<TermResponse?>

    suspend fun getTerm(parameterName: String, parameterType: String): ResponseFeedback<List<TermResponse>?>

    suspend fun deleteTerm(uniqueTermName: String): ResponseFeedback<String>

    suspend fun updateTerm(uniqueTermName: String, updatedTerm: TermRequest): ResponseFeedback<TermResponse?>

    
}



