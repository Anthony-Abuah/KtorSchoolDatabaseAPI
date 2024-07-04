package com.dedee.repository.term

import com.dedee.model.term.TermRequest
import com.dedee.model.term.TermResponse
import com.dedee.service.term.TermService
import util.Constants.emptyString
import util.DatabaseEntityFields.UniqueTermName
import util.Functions.uniqueSubjectIdGenerator
import util.Functions.uniqueTermIdGenerator
import util.ResponseFeedback

class TermRepositoryImpl(
    private val termService: TermService,
) : TermRepository{
    override suspend fun getAllTerms(): ResponseFeedback<List<TermResponse>?> {
        val termResponse = termService.getAllTerms()
        return if (termResponse.isNullOrEmpty()) {
            ResponseFeedback(
                data = null,
                message = "Could not fetch all terms",
                success = false
            )
        }else{
            ResponseFeedback(
                data = termResponse,
                message = "All terms successfully loaded",
                success = true
            )
        }
    }

    override suspend fun addTerm(term: TermRequest): ResponseFeedback<TermResponse?> {
        val termName = term.termName ?: emptyString
        val uniqueTermName = uniqueTermIdGenerator(termName)
        return if (uniqueTermIdExists(uniqueTermName)){
            ResponseFeedback(
                data = null,
                message = "Cannot add term because term already exists",
                success = false
            )
        }else{
            val registerResponse = termService.addTerm(term, uniqueTermName)
            if (registerResponse < 1 ) {
                ResponseFeedback(
                    data = null,
                    message = "Unable to add term",
                    success = false
                )
            }else{
                val registeredTerm = termService.getTerm(uniqueTermName, UniqueTermName)?.first()
                ResponseFeedback(
                    data = registeredTerm,
                    message = "Term successfully added",
                    success = true
                )
            }
        }
    }

    override suspend fun getTerm(
        parameterName: String,
        parameterType: String,
    ): ResponseFeedback<List<TermResponse>?> {
        val response = termService.getTerm(parameterName, parameterType)
        return if (response.isNullOrEmpty()){
            ResponseFeedback(
                data = null,
                message = "No term matches the given criteria",
                success = false
            )
        }else{
            ResponseFeedback(
                data = response,
                message = "Terms fetched successfully",
                success = true
            )
        }
    }

    override suspend fun deleteTerm(uniqueTermName: String): ResponseFeedback<String> {
        val response =  termService.deleteTerm(uniqueTermName) ?: -1
        return if (response < 1){
            ResponseFeedback(
                data = "Unable to delete term",
                message = "Unknown Error",
                success = false
            )
        }else{
            ResponseFeedback(
                data = "Term deleted successfully",
                message = "Term deleted successfully",
                success = true
            )
        }

    }

    override suspend fun updateTerm(
        uniqueTermName: String,
        updatedTerm: TermRequest,
    ): ResponseFeedback<TermResponse?> {
        val response = termService.updateTerm(uniqueTermName, updatedTerm)
        return if (response == null){
            ResponseFeedback(
                data = null,
                message = "Unable to update term",
                success = false
            )
        }else{
            ResponseFeedback(
                data = response,
                message = "Term updated successfully",
                success = true
            )
        }
    }


    private suspend fun uniqueTermIdExists(uniqueTermId: String): Boolean {
        return !(termService.getTerm(uniqueTermId, UniqueTermName).isNullOrEmpty())
    }

}