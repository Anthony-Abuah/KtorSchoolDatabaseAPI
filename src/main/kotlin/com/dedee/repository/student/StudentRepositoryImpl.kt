package com.dedee.repository.student

import com.dedee.model.student.StudentRequest
import com.dedee.model.student.StudentResponse
import com.dedee.model.subject.SubjectInfo
import com.dedee.model.teacher.TeacherInfo
import com.dedee.service.helper.subject_class.SubjectClassService
import com.dedee.service.helper.teacher_class.TeacherClassService
import com.dedee.service.student.StudentService
import com.google.gson.Gson
import util.Constants.emptyString
import util.DatabaseEntityFields.FirstName
import util.DatabaseEntityFields.LastName
import util.DatabaseEntityFields.OtherNames
import util.DatabaseEntityFields.UniqueStudentId
import util.Functions.uniqueStudentIdGenerator
import util.ResponseFeedback

class StudentRepositoryImpl(
    private val studentService: StudentService,
    private val teacherClassService: TeacherClassService,
    private val subjectClassService: SubjectClassService,
) : StudentRepository{
    override suspend fun getAllStudents(): ResponseFeedback<List<StudentResponse>?> {
        val studentResponse = studentService.getAllStudents()
        return if (studentResponse.isNullOrEmpty()) {
            ResponseFeedback(
                data = null,
                message = "Could not fetch all students",
                success = false
            )
        }else{
            ResponseFeedback(
                data = studentResponse,
                message = "All students successfully loaded",
                success = true
            )
        }
    }

    override suspend fun registerStudent(student: StudentRequest): ResponseFeedback<StudentResponse?> {
        val firstName = student.firstName
        val lastName = student.lastName
        val otherNames = student.otherNames ?: emptyString
        val uniqueStudentId = uniqueStudentIdGenerator(firstName, lastName)
        return if (uniqueStudentIdExists(uniqueStudentId)){
            ResponseFeedback(
                data = null,
                message = "Cannot register student because studentId already exists",
                success = false
            )
        }else if (studentWithThisName(firstName, lastName, otherNames)){
            ResponseFeedback(
                data = null,
                message = "Cannot register student because student with all provided names already exists",
                success = false
            )
        }else{
            val uniqueClassId = student.uniqueClassId
            val teacherItems: List<TeacherInfo>? = teacherClassService.getAllTeachersOfThisClass(uniqueClassId)
            val subjectItems: List<SubjectInfo>? = subjectClassService.getAllSubjectsOfThisClass(uniqueClassId)
            val gson = Gson()
            val teachers = gson.toJson(teacherItems)
            val subjects = gson.toJson(subjectItems)

            val registerResponse = studentService.registerStudent(student, uniqueStudentId, teachers.toString(), subjects.toString())
            if (registerResponse < 1 ) {
                ResponseFeedback(
                    data = null,
                    message = "Unable to register student",
                    success = false
                )
            }else{
                val registeredStudent = studentService.getStudents(uniqueStudentId, UniqueStudentId)?.first()
                ResponseFeedback(
                    data = registeredStudent,
                    message = "Student successfully registered",
                    success = true
                )


            }
        }
    }

    override suspend fun getStudentByParameter(
        parameterName: String,
        parameterType: String,
    ): ResponseFeedback<List<StudentResponse>?> {
        val response = studentService.getStudents(parameterName, parameterType)
        return if (response.isNullOrEmpty()){
            ResponseFeedback(
                data = null,
                message = "No student matches the given criteria",
                success = false
            )
        }else{
            ResponseFeedback(
                data = response,
                message = "Students fetched successfully",
                success = true
            )
        }
    }

    override suspend fun deleteStudent(uniqueStudentId: String): ResponseFeedback<String> {
        val response =  studentService.deleteStudent(uniqueStudentId) ?: -1
        return if (response < 1){
            ResponseFeedback(
                data = "Unable to delete student",
                message = "Unknown Error",
                success = false
            )
        }else{
            ResponseFeedback(
                data = "Student deleted successfully",
                message = "Student deleted successfully",
                success = true
            )
        }

    }

    override suspend fun updateStudent(
        uniqueStudentId: String,
        updatedStudent: StudentRequest,
    ): ResponseFeedback<StudentResponse?> {
        val response = studentService.updateStudent(uniqueStudentId, updatedStudent)
        return if (response == null){
            ResponseFeedback(
                data = null,
                message = "Unable to update student",
                success = false
            )
        }else{
            ResponseFeedback(
                data = response,
                message = "Student updated successfully",
                success = true
            )
        }
    }


    private suspend fun uniqueStudentIdExists(uniqueStudentId: String): Boolean {
        return !(studentService.getStudents(uniqueStudentId, UniqueStudentId).isNullOrEmpty())
    }
    private suspend fun studentWithThisName(firstName: String, lastName: String, otherNames: String): Boolean {
        val firstnameExists = !(studentService.getStudents(firstName, FirstName).isNullOrEmpty())
        val lastNameExists = !(studentService.getStudents(lastName, LastName).isNullOrEmpty())
        val otherNamesExists = !(studentService.getStudents(otherNames, OtherNames).isNullOrEmpty())
        return (firstnameExists && lastNameExists && otherNamesExists)
    }

}