package com.dedee.route

import com.dedee.model.term.TermRequest
import com.dedee.repository.term.TermRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import util.Constants.emptyString
import util.Constants.parameter
import util.Constants.parameterType
import util.Routes.deleteTerm
import util.Routes.getAllTerms
import util.Routes.getTerms
import util.Routes.registerTerm
import util.Routes.updateTerm

fun Application.termRoutes(termRepository: TermRepository) {
    routing {
        get(getAllTerms) {
            val result = termRepository.getAllTerms()
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }
        get(getTerms) {
            val parameter = call.parameters[parameter] ?: emptyString
            val parameterType = call.parameters[parameterType] ?: emptyString
            val result = termRepository.getTerm(parameter, parameterType)
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }

        post (registerTerm){
            val termRequest = call.receive<TermRequest>()
            val result = termRepository.addTerm(termRequest)
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }

        put(updateTerm){
            val parameter = call.parameters[parameter] ?: emptyString
            val termRequest = call.receive<TermRequest>()
            val result = termRepository.updateTerm(parameter, termRequest)
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }

        delete (deleteTerm){
            val parameter = call.parameters[parameter] ?: emptyString
            val result = termRepository.deleteTerm(parameter)
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }
    }
}
