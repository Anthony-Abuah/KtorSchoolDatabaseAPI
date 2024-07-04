package com.dedee.route

import com.dedee.model.school_class.SchoolClassRequest
import com.dedee.repository.school_class.SchoolClassRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import util.Constants.emptyString
import util.Constants.parameter
import util.Constants.parameterType
import util.Routes.deleteClass
import util.Routes.getAllClasses
import util.Routes.getClasses
import util.Routes.registerClass
import util.Routes.updateClass

fun Application.classRoutes(schoolClassRepository: SchoolClassRepository) {
    routing {
        get(getAllClasses) {
            val result = schoolClassRepository.getAllSchoolClasses()
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }
        get(getClasses) {
            val parameter = call.parameters[parameter] ?: emptyString
            val parameterType = call.parameters[parameterType] ?: emptyString
            val result = schoolClassRepository.getSchoolClass(parameter, parameterType)
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }

        post (registerClass){
            val schoolClassRequest = call.receive<SchoolClassRequest>()
            val result = schoolClassRepository.addSchoolClass(schoolClassRequest)
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }

        put(updateClass){
            val parameter = call.parameters[parameter] ?: emptyString
            val schoolClassRequest = call.receive<SchoolClassRequest>()
            val result = schoolClassRepository.updateSchoolClass(parameter, schoolClassRequest)
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }


        delete (deleteClass){
            val parameter = call.parameters[parameter] ?: emptyString
            val result = schoolClassRepository.deleteSchoolClass(parameter)
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }
    }
}
