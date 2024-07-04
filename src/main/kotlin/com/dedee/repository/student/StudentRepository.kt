package com.dedee.repository.student

import com.dedee.model.student.StudentRequest
import com.dedee.model.student.StudentResponse
import util.ResponseFeedback


interface StudentRepository {
    suspend fun getAllStudents(): ResponseFeedback<List<StudentResponse>?>

    suspend fun registerStudent(student: StudentRequest): ResponseFeedback<StudentResponse?>

    suspend fun getStudentByParameter(parameterName: String, parameterType: String): ResponseFeedback<List<StudentResponse>?>

    suspend fun deleteStudent(uniqueStudentId: String): ResponseFeedback<String>

    suspend fun updateStudent(uniqueStudentId: String, updatedStudent: StudentRequest): ResponseFeedback<StudentResponse?>

    
}



