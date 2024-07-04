package com.dedee.route

import com.dedee.model.time_table.TimeTableRequest
import com.dedee.repository.time_table.TimeTableRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import util.Constants.emptyString
import util.DatabaseEntityFields.UniqueTimeTableId
import util.Routes.deleteTimeTable
import util.Routes.getAllTimeTables
import util.Routes.getTimeTable
import util.Routes.registerTimeTable
import util.Routes.updateTimeTable

fun Application.timeTableRoutes(timeTableRepository: TimeTableRepository) {
    routing {
        get(getAllTimeTables) {
            val result = timeTableRepository.getAllTimeTables()
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }
        get(getTimeTable) {
            val uniqueTimeTableId = call.parameters[UniqueTimeTableId] ?: emptyString
            val result = timeTableRepository.getTimeTable(uniqueTimeTableId)
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }

        post (registerTimeTable){
            val timeTableRequest = call.receive<TimeTableRequest>()
            val result = timeTableRepository.addTimeTable(timeTableRequest)
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }

        put(updateTimeTable){
            val uniqueTimeTableId = call.parameters[UniqueTimeTableId] ?: emptyString
            val timeTableRequest = call.receive<TimeTableRequest>()
            val result = timeTableRepository.updateTimeTable(uniqueTimeTableId, timeTableRequest)
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }


        delete (deleteTimeTable){
            val uniqueTimeTableId = call.parameters[UniqueTimeTableId] ?: emptyString
            val result = timeTableRepository.deleteTimeTable(uniqueTimeTableId)
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }
    }
}
