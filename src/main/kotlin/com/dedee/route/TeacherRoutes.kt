package com.dedee.route

import com.dedee.model.teacher.TeacherRequest
import com.dedee.repository.teacher.TeacherRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import util.Constants.currentPassword
import util.Constants.emptyString
import util.Constants.parameter
import util.Constants.updatedPassword
import util.DatabaseEntityFields.Email
import util.DatabaseEntityFields.TeacherUsername
import util.Routes.changeTeacherPasswords
import util.Routes.deleteTeacher
import util.Routes.getAllTeachers
import util.Routes.getTeacherByEmail
import util.Routes.getTeacherByTeacherUsername
import util.Routes.registerTeacher
import util.Routes.updateTeacher

fun Application.teacherRoute(teacherRepository: TeacherRepository) {
    routing {
        get(getAllTeachers) {
            val result = teacherRepository.getAllTeachers()
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }
        get(getTeacherByEmail) {
            val email = call.parameters[Email] ?: emptyString
            val result = teacherRepository.getTeacherByEmail(email)
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }
        get(getTeacherByTeacherUsername) {
            val teacherUsername = call.parameters[TeacherUsername] ?: emptyString
            val result = teacherRepository.getTeacherByUsername(teacherUsername)
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }


        post (registerTeacher){
            val teacherRequest = call.receive<TeacherRequest>()
            val result = teacherRepository.registerTeacher(teacherRequest)
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }

        put(updateTeacher){
            val parameter = call.parameters[parameter] ?: emptyString
            val teacherRequest = call.receive<TeacherRequest>()
            val result = teacherRepository.updateTeacher(parameter, teacherRequest)
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }

        put(changeTeacherPasswords){
            val parameter = call.parameters[parameter] ?: emptyString
            val currentPassword = call.parameters[currentPassword] ?: emptyString
            val updatedPassword = call.parameters[updatedPassword] ?: emptyString
            val result = teacherRepository.changeTeacherPassword(parameter, currentPassword, updatedPassword)
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }

        delete (deleteTeacher){
            val parameter = call.parameters[parameter] ?: emptyString
            val result = teacherRepository.deleteTeacher(parameter)
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }
    }
}
