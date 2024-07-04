package com.dedee.route

import com.dedee.model.conduct.ConductRequest
import com.dedee.repository.conduct.ConductRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import util.Constants.emptyString
import util.DatabaseEntityFields.UniqueConductId
import util.Routes.deleteConduct
import util.Routes.getAllConducts
import util.Routes.getConduct
import util.Routes.registerConduct
import util.Routes.updateConduct

fun Application.conductRoutes(conductRepository: ConductRepository) {
    routing {
        get(getAllConducts) {
            val result = conductRepository.getAllConducts()
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }
        get(getConduct) {
            val uniqueConductId = call.parameters[UniqueConductId] ?: emptyString
            val result = conductRepository.getConduct(uniqueConductId)
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }

        post (registerConduct){
            val conductRequest = call.receive<ConductRequest>()
            val result = conductRepository.addConduct(conductRequest)
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }

        put(updateConduct){
            val uniqueConductId = call.parameters[UniqueConductId] ?: emptyString
            val conductRequest = call.receive<ConductRequest>()
            val result = conductRepository.updateConduct(uniqueConductId, conductRequest)
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }


        delete (deleteConduct){
            val uniqueConductId = call.parameters[UniqueConductId] ?: emptyString
            val result = conductRepository.deleteConduct(uniqueConductId)
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }
    }
}
