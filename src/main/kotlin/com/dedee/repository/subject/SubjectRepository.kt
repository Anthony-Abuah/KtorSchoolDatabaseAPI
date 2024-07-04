package com.dedee.repository.subject

import com.dedee.model.subject.SubjectRequest
import com.dedee.model.subject.SubjectResponse
import util.ResponseFeedback


interface SubjectRepository {
    suspend fun getAllSubjects(): ResponseFeedback<List<SubjectResponse>?>

    suspend fun addSubject(subject: SubjectRequest): ResponseFeedback<SubjectResponse?>

    suspend fun getSubject(parameterName: String, parameterType: String): ResponseFeedback<List<SubjectResponse>?>

    suspend fun deleteSubject(uniqueSubjectId: String): ResponseFeedback<String>

    suspend fun updateSubject(uniqueSubjectId: String, updatedSubject: SubjectRequest): ResponseFeedback<SubjectResponse?>

    
}



