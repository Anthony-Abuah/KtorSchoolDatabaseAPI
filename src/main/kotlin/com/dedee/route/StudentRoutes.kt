package com.dedee.route

import com.dedee.model.student.StudentRequest
import com.dedee.repository.student.StudentRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import util.Constants.emptyString
import util.Constants.parameter
import util.Constants.parameterType
import util.Routes.deleteStudent
import util.Routes.getAllStudents
import util.Routes.getStudents
import util.Routes.registerStudent
import util.Routes.updateStudent

fun Application.studentRoutes(studentRepository: StudentRepository) {
    routing {
        get(getAllStudents) {
            val result = studentRepository.getAllStudents()
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }
        get(getStudents) {
            val parameter = call.parameters[parameter] ?: emptyString
            val parameterType = call.parameters[parameterType] ?: emptyString
            val result = studentRepository.getStudentByParameter(parameter, parameterType)
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }

        post (registerStudent){
            val studentRequest = call.receive<StudentRequest>()
            val result = studentRepository.registerStudent(studentRequest)
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }

        put(updateStudent){
            val parameter = call.parameters[parameter] ?: emptyString
            val studentRequest = call.receive<StudentRequest>()
            val result = studentRepository.updateStudent(parameter, studentRequest)
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }


        delete (deleteStudent){
            val parameter = call.parameters[parameter] ?: emptyString
            val result = studentRepository.deleteStudent(parameter)
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }
    }
}
