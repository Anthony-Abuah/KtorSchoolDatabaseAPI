package com.dedee.route

import com.dedee.model.subject.SubjectRequest
import com.dedee.repository.subject.SubjectRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import util.Constants.emptyString
import util.Constants.parameter
import util.Constants.parameterType
import util.Routes.deleteSubject
import util.Routes.getAllSubjects
import util.Routes.getSubjects
import util.Routes.registerSubject
import util.Routes.updateSubject

fun Application.subjectRoutes(subjectRepository: SubjectRepository) {
    routing {
        get(getAllSubjects) {
            val result = subjectRepository.getAllSubjects()
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }
        get(getSubjects) {
            val parameter = call.parameters[parameter] ?: emptyString
            val parameterType = call.parameters[parameterType] ?: emptyString
            val result = subjectRepository.getSubject(parameter, parameterType)
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }

        post (registerSubject){
            val subjectRequest = call.receive<SubjectRequest>()
            val result = subjectRepository.addSubject(subjectRequest)
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }

        put(updateSubject){
            val parameter = call.parameters[parameter] ?: emptyString
            val subjectRequest = call.receive<SubjectRequest>()
            val result = subjectRepository.updateSubject(parameter, subjectRequest)
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }


        delete (deleteSubject){
            val parameter = call.parameters[parameter] ?: emptyString
            val result = subjectRepository.deleteSubject(parameter)
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }
    }
}
