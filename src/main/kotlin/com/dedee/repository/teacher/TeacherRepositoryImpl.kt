package com.dedee.repository.teacher

import com.dedee.model.teacher.TeacherRequest
import com.dedee.model.teacher.TeacherResponse
import com.dedee.security.hashing.HashService
import com.dedee.service.teacher.TeacherService
import util.Constants.emptyString
import util.Functions.uniqueTeacherIdGenerator
import util.ResponseFeedback

class TeacherRepositoryImpl(
    private val teacherService: TeacherService,
    private val hashService: HashService,
) : TeacherRepository{
    override suspend fun getAllTeachers(): ResponseFeedback<List<TeacherResponse>> {
        val teacherResponse = teacherService.getAllTeachers()
        return ResponseFeedback(
            data = teacherResponse,
            message = "Successfully loaded",
            success = true
        )
    }

    override suspend fun registerTeacher(teacher: TeacherRequest): ResponseFeedback<TeacherResponse?> {
        val firstName = teacher.firstName ?: emptyString
        val lastName = teacher.lastName ?: emptyString
        val otherNames = teacher.otherNames ?: emptyString
        val uniqueTeacherId = uniqueTeacherIdGenerator(firstName, lastName)
        val password = teacher.password
        val saltedHash = hashService.generateSaltedHash(password)
        val hashedPassword = saltedHash.hash
        val salt = saltedHash.salt
        val email = teacher.email
        //Check if teacher already exists
        //Check if username exists
        return if (teacherUserNameExists(uniqueTeacherId)){
            ResponseFeedback(
                data = null,
                message = "Teacher with name: $firstName $otherNames $lastName already exists",
                success = false
            )
        }else if (emailExists(email)){
            ResponseFeedback(
                data = null,
                message = "Teacher with email: $email exists",
                success = false
            )
        }else if (passwordIsNotValid(password, firstName, lastName)){
            ResponseFeedback(
                data = null,
                message = "Password is weak",
                success = false
            )
        }else{
            teacherService.registerTeacher(teacher, uniqueTeacherId, hashedPassword, salt)
            val thisTeacher = teacherService.findTeacherByEmail(email)
            ResponseFeedback(
                data = thisTeacher,
                message = "Teacher successfully added",
                success = true
            )
        }

    }

    override suspend fun getTeacherByEmail(email: String): ResponseFeedback<TeacherResponse> {
        val response = teacherService.findTeacherByEmail(email)
        return if (response != null){
            ResponseFeedback(
                data = response,
                message = "Teacher successfully fetched",
                success = true
            )
        }else{
            ResponseFeedback(
                data = null,
                message = "Teacher with $email does not exist",
                success = false
            )
        }
    }

    override suspend fun getTeacherByUsername(teacherUsername: String): ResponseFeedback<TeacherResponse> {
        val response = teacherService.findTeacherByTeacherUserName(teacherUsername)
        return if (response != null){
            ResponseFeedback(
                data = response,
                message = "Teacher successfully fetched",
                success = true
            )
        }else{
            ResponseFeedback(
                data = null,
                message = "Teacher with $teacherUsername does not exist",
                success = false
            )
        }
    }

    override suspend fun deleteTeacher(uniqueTeacherId: String): ResponseFeedback<String> {
        val response = teacherService.deleteTeacher(uniqueTeacherId) ?: -1
        return if (response < 1) {
            ResponseFeedback(
                data = "Could not delete teacher",
                message = "Unknown error",
                success = false
            )
        } else {
            ResponseFeedback(
                data = "Teacher successfully deleted",
                message = "Teacher successfully deleted",
                success = true
            )
        }
    }

    override suspend fun updateTeacher(
        uniqueTeacherId: String,
        updatedTeacher: TeacherRequest,
    ): ResponseFeedback<TeacherResponse> {
        val response = teacherService.updateTeacher(uniqueTeacherId, updatedTeacher)
        return if (response != null) {
            ResponseFeedback(
                data = response,
                message = "Teacher successfully updated",
                success = true
            )
        } else {
            ResponseFeedback(
                data = null,
                message = "Teacher with this credential :$uniqueTeacherId does not exist",
                success = false
            )
        }
    }

    override suspend fun changeTeacherPassword(
        uniqueTeacherId: String,
        currentPassword: String,
        updatedPassword: String,
    ): ResponseFeedback<TeacherResponse> {
        val teacher = teacherService.findTeacherByUniqueTeacherId(uniqueTeacherId)
        val firstName = teacher?.firstName ?: emptyString
        val lastName = teacher?.lastName ?: emptyString
        val password = teacher?.password ?: emptyString
        val salt = teacher?.salt ?: emptyString

        val saltedHash = hashService.generateSaltedHash(updatedPassword)
        val updatedHashedPassword = saltedHash.hash
        val updatedSalt = saltedHash.salt
        val passwordDoesNotMatch = !hashService.verify(currentPassword, salt, password)

        val passwordIsNotValid = passwordIsNotValid(updatedPassword, firstName, lastName)
        return if (passwordDoesNotMatch){
            ResponseFeedback(
                data = null,
                message = "Passwords do no match",
                success = false
            )
        }else if (passwordIsNotValid){
            ResponseFeedback(
                data = null,
                message = "Password is weak or already in use",
                success = false
            )
        }else{
            val response = teacherService.changeTeacherPassword(uniqueTeacherId, updatedHashedPassword, updatedSalt)
            ResponseFeedback(
                data = response,
                message = "Password successfully changed",
                success = true
            )
        }
    }


    private suspend fun emailExists(email: String): Boolean {
        return teacherService.findTeacherByEmail(email) != null
    }
    private suspend fun teacherUserNameExists(teacherUsername: String): Boolean {
        return teacherService.findTeacherByTeacherUserName(teacherUsername) != null
    }
    private suspend fun passwordIsNotValid(password: String, firstName: String, lastName: String): Boolean {
        return teacherService.passwordIsNotValid(password, firstName, lastName)
    }
}