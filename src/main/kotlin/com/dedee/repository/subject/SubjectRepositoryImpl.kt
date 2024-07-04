package com.dedee.repository.subject

import com.dedee.model.subject.SubjectRequest
import com.dedee.model.subject.SubjectResponse
import com.dedee.service.subject.SubjectService
import util.Constants.emptyString
import util.DatabaseEntityFields.UniqueSubjectId
import util.Functions.uniqueSubjectIdGenerator
import util.ResponseFeedback

class SubjectRepositoryImpl(
    private val subjectService: SubjectService,
) : SubjectRepository{
    override suspend fun getAllSubjects(): ResponseFeedback<List<SubjectResponse>?> {
        val subjectResponse = subjectService.getAllSubjects()
        return if (subjectResponse.isNullOrEmpty()) {
            ResponseFeedback(
                data = null,
                message = "Could not fetch all subjects",
                success = false
            )
        }else{
            ResponseFeedback(
                data = subjectResponse,
                message = "All subjects successfully loaded",
                success = true
            )
        }
    }

    override suspend fun addSubject(subject: SubjectRequest): ResponseFeedback<SubjectResponse?> {
        val subjectName = subject.subjectName ?: emptyString
        val uniqueSubjectId = uniqueSubjectIdGenerator(subjectName)
        return if (uniqueSubjectIdExists(uniqueSubjectId)){
            ResponseFeedback(
                data = null,
                message = "Cannot add subject because subject already exists",
                success = false
            )
        }else{
            val registerResponse = subjectService.addSubject(subject, uniqueSubjectId)
            if (registerResponse < 1 ) {
                ResponseFeedback(
                    data = null,
                    message = "Unable to register subject",
                    success = false
                )
            }else{
                val registeredSubject = subjectService.getSubject(uniqueSubjectId, UniqueSubjectId)?.first()
                ResponseFeedback(
                    data = registeredSubject,
                    message = "Subject successfully registered",
                    success = true
                )
            }
        }
    }

    override suspend fun getSubject(
        parameterName: String,
        parameterType: String,
    ): ResponseFeedback<List<SubjectResponse>?> {
        val response = subjectService.getSubject(parameterName, parameterType)
        return if (response.isNullOrEmpty()){
            ResponseFeedback(
                data = null,
                message = "No subject matches the given criteria",
                success = false
            )
        }else{
            ResponseFeedback(
                data = response,
                message = "Subjects fetched successfully",
                success = true
            )
        }
    }

    override suspend fun deleteSubject(uniqueSubjectId: String): ResponseFeedback<String> {
        val response =  subjectService.deleteSubject(uniqueSubjectId) ?: -1
        return if (response < 1){
            ResponseFeedback(
                data = "Unable to delete subject",
                message = "Unknown Error",
                success = false
            )
        }else{
            ResponseFeedback(
                data = "Subject deleted successfully",
                message = "Subject deleted successfully",
                success = true
            )
        }

    }

    override suspend fun updateSubject(
        uniqueSubjectId: String,
        updatedSubject: SubjectRequest,
    ): ResponseFeedback<SubjectResponse?> {
        val response = subjectService.updateSubject(uniqueSubjectId, updatedSubject)
        return if (response == null){
            ResponseFeedback(
                data = null,
                message = "Unable to update subject",
                success = false
            )
        }else{
            ResponseFeedback(
                data = response,
                message = "Subject updated successfully",
                success = true
            )
        }
    }


    private suspend fun uniqueSubjectIdExists(uniqueSubjectId: String): Boolean {
        return !(subjectService.getSubject(uniqueSubjectId, UniqueSubjectId).isNullOrEmpty())
    }

}