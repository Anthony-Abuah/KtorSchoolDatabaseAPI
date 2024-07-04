package com.dedee.service.student

import com.dedee.model.grading.GradingInfo
import com.dedee.model.student.StudentRequest
import com.dedee.model.student.StudentResponse

interface StudentService {

    suspend fun getAllStudents(): List<StudentResponse>?

    suspend fun registerStudent(student: StudentRequest, uniqueStudentId: String, teachers:String, subjects:String): Int

    suspend fun getStudents(parameterName: String, parameterType: String): List<StudentResponse>?

    suspend fun updateStudent(uniqueStudentId: String, updatedStudent: StudentRequest): StudentResponse?

    suspend fun addParents(parents: String, uniqueStudentId: String)

    suspend fun addTeachers(teachers: String, uniqueStudentId: String)

    suspend fun addSubjects(subjects: String, uniqueStudentId: String)

    suspend fun addConduct(conduct: String, uniqueStudentId: String)

    suspend fun addGradings(gradings: String, uniqueStudentId: String): List<GradingInfo>?

    suspend fun deleteStudent(uniqueStudentId: String): Int?

}