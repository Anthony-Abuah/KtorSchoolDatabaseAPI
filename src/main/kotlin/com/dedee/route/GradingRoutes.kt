package com.dedee.route

import com.dedee.model.grading.GradingRequest
import com.dedee.repository.grading.GradingRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import util.Constants.emptyString
import util.Constants.parameter
import util.DatabaseEntityFields.UniqueClassId
import util.DatabaseEntityFields.UniqueGradingId
import util.DatabaseEntityFields.UniqueStudentId
import util.DatabaseEntityFields.UniqueSubjectId
import util.DatabaseEntityFields.UniqueTermName
import util.Routes.deleteGrading
import util.Routes.getAllGradings
import util.Routes.getClassGradings
import util.Routes.getGrading
import util.Routes.getStudentGradings
import util.Routes.registerGrading
import util.Routes.updateGrading

fun Application.gradingRoutes(gradingRepository: GradingRepository) {
    routing {
        get(getAllGradings) {
            val result = gradingRepository.getAllGradings()
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }
        get(getGrading) {
            val uniqueGradingId = call.parameters[UniqueGradingId] ?: emptyString
            val result = gradingRepository.getGrading(uniqueGradingId)
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }
        get(getClassGradings) {
            val uniqueClassId = call.parameters[UniqueClassId] ?: emptyString
            val uniqueSubjectId = call.parameters[UniqueSubjectId] ?: emptyString
            val uniqueTermName = call.parameters[UniqueTermName] ?: emptyString
            val result = gradingRepository.getClassGradings(uniqueClassId, uniqueSubjectId, uniqueTermName)
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }
        get(getStudentGradings) {
            val uniqueStudentId = call.parameters[UniqueStudentId] ?: emptyString
            val uniqueClassId = call.parameters[UniqueClassId] ?: emptyString
            val uniqueSubjectId = call.parameters[UniqueSubjectId]
            val uniqueTermName = call.parameters[UniqueTermName]
            val result = gradingRepository.getStudentGradings(uniqueStudentId, uniqueClassId, uniqueSubjectId, uniqueTermName)
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }

        post (registerGrading){
            val gradingRequest = call.receive<GradingRequest>()
            val result = gradingRepository.addGrading(gradingRequest)
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }

        put(updateGrading){
            val parameter = call.parameters[parameter] ?: emptyString
            val gradingRequest = call.receive<GradingRequest>()
            val result = gradingRepository.updateGrading(parameter, gradingRequest)
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }


        delete (deleteGrading){
            val parameter = call.parameters[parameter] ?: emptyString
            val result = gradingRepository.deleteGrading(parameter)
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }
    }
}
