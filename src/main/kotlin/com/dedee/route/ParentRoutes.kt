package com.dedee.route

import com.dedee.model.parent.ParentRequest
import com.dedee.repository.parent.ParentRepository
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
import util.DatabaseEntityFields.ParentUsername
import util.Routes.changeParentPasswords
import util.Routes.deleteParent
import util.Routes.getAllParents
import util.Routes.getParentByEmail
import util.Routes.getParentByParentUsername
import util.Routes.registerParent
import util.Routes.updateParent

fun Application.parentRoute(parentRepository: ParentRepository) {
    routing {
        get(getAllParents) {
            val result = parentRepository.getAllParents()
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }
        get(getParentByEmail) {
            val email = call.parameters[Email] ?: emptyString
            val result = parentRepository.getParentByEmail(email)
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }
        get(getParentByParentUsername) {
            val parentUsername = call.parameters[ParentUsername] ?: emptyString
            val result = parentRepository.getParentByUsername(parentUsername)
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }

        post (registerParent){
            val parentRequest = call.receive<ParentRequest>()
            val result = parentRepository.registerParent(parentRequest)
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }

        put(updateParent){
            val parameter = call.parameters[parameter] ?: emptyString
            val parentRequest = call.receive<ParentRequest>()
            val result = parentRepository.updateParent(parameter, parentRequest)
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }

        put(changeParentPasswords){
            val parameter = call.parameters[parameter] ?: emptyString
            val currentPassword = call.parameters[currentPassword] ?: emptyString
            val updatedPassword = call.parameters[updatedPassword] ?: emptyString
            val result = parentRepository.changeParentPassword(parameter, currentPassword, updatedPassword)
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }

        delete (deleteParent){
            val parameter = call.parameters[parameter] ?: emptyString
            val result = parentRepository.deleteParent(parameter)
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }
    }
}
