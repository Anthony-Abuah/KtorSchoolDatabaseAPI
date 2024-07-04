package com.dedee.service.subject

import com.dedee.model.subject.SubjectRequest
import com.dedee.model.subject.SubjectResponse

interface SubjectService {

    suspend fun getAllSubjects(): List<SubjectResponse>?

    suspend fun addClasses(classes: String, uniqueSubjectId: String)

    suspend fun addTeachers(teachers: String, uniqueSubjectId: String)

    suspend fun addSubject(subject: SubjectRequest, uniqueSubjectId: String): Int

    suspend fun getSubject(parameterName: String, parameterType: String): List<SubjectResponse>?

    suspend fun updateSubject(uniqueSubjectId: String, updatedSubject: SubjectRequest): SubjectResponse?

    suspend fun deleteSubject(uniqueSubjectId: String): Int?

}