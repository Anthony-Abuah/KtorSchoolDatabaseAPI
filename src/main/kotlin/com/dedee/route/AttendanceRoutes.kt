package com.dedee.route

import com.dedee.model.attendance.AttendanceRequest
import com.dedee.repository.attendance.AttendanceRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import util.Constants.emptyString
import util.DatabaseEntityFields.UniqueAttendanceId
import util.Routes.deleteAttendance
import util.Routes.getAllAttendances
import util.Routes.getAttendance
import util.Routes.registerAttendance
import util.Routes.updateAttendance

fun Application.attendanceRoutes(attendanceRepository: AttendanceRepository) {
    routing {
        get(getAllAttendances) {
            val result = attendanceRepository.getAllAttendances()
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }
        get(getAttendance) {
            val uniqueAttendanceId = call.parameters[UniqueAttendanceId] ?: emptyString
            val result = attendanceRepository.getAttendance(uniqueAttendanceId)
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }

        post (registerAttendance){
            val attendanceRequest = call.receive<AttendanceRequest>()
            val result = attendanceRepository.addAttendance(attendanceRequest)
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }

        put(updateAttendance){
            val uniqueAttendanceId = call.parameters[UniqueAttendanceId] ?: emptyString
            val attendanceRequest = call.receive<AttendanceRequest>()
            val result = attendanceRepository.updateAttendance(uniqueAttendanceId, attendanceRequest)
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }


        delete (deleteAttendance){
            val uniqueAttendanceId = call.parameters[UniqueAttendanceId] ?: emptyString
            val result = attendanceRepository.deleteAttendance(uniqueAttendanceId)
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }
    }
}
