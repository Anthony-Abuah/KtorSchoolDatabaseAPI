package com.dedee.route

import com.dedee.model.daily_attendance.DailyAttendanceRequest
import com.dedee.repository.daily_attendance.DailyAttendanceRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import util.Constants.emptyString
import util.DatabaseEntityFields.UniqueDailyAttendanceId
import util.Routes.deleteDailyAttendance
import util.Routes.getAllDailyAttendances
import util.Routes.getDailyAttendance
import util.Routes.registerDailyAttendance
import util.Routes.updateDailyAttendance

fun Application.dailyAttendanceRoutes(dailyAttendanceRepository: DailyAttendanceRepository) {
    routing {
        get(getAllDailyAttendances) {
            val result = dailyAttendanceRepository.getAllDailyAttendances()
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }
        get(getDailyAttendance) {
            val uniqueDailyAttendanceId = call.parameters[UniqueDailyAttendanceId] ?: emptyString
            val result = dailyAttendanceRepository.getDailyAttendance(uniqueDailyAttendanceId)
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }

        post (registerDailyAttendance){
            val dailyAttendanceRequest = call.receive<DailyAttendanceRequest>()
            val result = dailyAttendanceRepository.addDailyAttendance(dailyAttendanceRequest)
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }

        put(updateDailyAttendance){
            val uniqueDailyAttendanceId = call.parameters[UniqueDailyAttendanceId] ?: emptyString
            val dailyAttendanceRequest = call.receive<DailyAttendanceRequest>()
            val result = dailyAttendanceRepository.updateDailyAttendance(uniqueDailyAttendanceId, dailyAttendanceRequest)
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }


        delete (deleteDailyAttendance){
            val uniqueDailyAttendanceId = call.parameters[UniqueDailyAttendanceId] ?: emptyString
            val result = dailyAttendanceRepository.deleteDailyAttendance(uniqueDailyAttendanceId)
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }
    }
}
