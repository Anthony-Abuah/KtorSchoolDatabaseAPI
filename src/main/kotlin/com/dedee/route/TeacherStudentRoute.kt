package com.dedee.route

import com.dedee.model.helper_tables.TeacherStudent
import com.dedee.repository.helper.teacher_student.TeacherStudentRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import util.Constants.emptyString
import util.DatabaseEntityFields.UniqueStudentId
import util.DatabaseEntityFields.UniqueTeacherId
import util.Routes.addTeacherStudent
import util.Routes.deleteTeacherStudent
import util.Routes.getAllTeacherStudents
import util.Routes.getStudentsOfThisTeacher
import util.Routes.getTeachersOfThisStudent
import util.Routes.updateTeacherStudent
import util.Routes.updatedStudentId
import util.Routes.updatedTeacherId

fun Application.teacherStudentRoute(teacherStudentRepository: TeacherStudentRepository) {
    routing {
        get(getAllTeacherStudents) {
            val result = teacherStudentRepository.getAllTeacherStudents()
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }
        get(getTeachersOfThisStudent) {
            val uniqueStudentId = call.parameters[UniqueStudentId] ?: emptyString
            val result = teacherStudentRepository.getAllTeachersOfThisStudent(uniqueStudentId)
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }

        get(getStudentsOfThisTeacher) {
            val uniqueStudentId = call.parameters[UniqueTeacherId] ?: emptyString
            val result = teacherStudentRepository.getAllStudentsOfThisTeacher(uniqueStudentId)
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }

        post (addTeacherStudent){
            val uniqueStudentId = call.parameters[UniqueStudentId] ?: emptyString
            val uniqueTeacherId = call.parameters[UniqueStudentId] ?: emptyString
            val result = teacherStudentRepository.addTeacherStudent(TeacherStudent(uniqueTeacherId, uniqueStudentId))
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }

        put(updateTeacherStudent){
            val uniqueStudentId = call.parameters[UniqueStudentId] ?: emptyString
            val uniqueTeacherId = call.parameters[UniqueTeacherId] ?: emptyString
            val thisUpdatedTeacherId = call.parameters[updatedTeacherId] ?: emptyString
            val thisUpdatedStudentId = call.parameters[updatedStudentId] ?: emptyString
            val result = teacherStudentRepository.updateTeacherStudent(uniqueTeacherId, uniqueStudentId, TeacherStudent(thisUpdatedTeacherId,thisUpdatedStudentId))
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }


        delete (deleteTeacherStudent){
            val uniqueStudentId = call.parameters[UniqueStudentId] ?: emptyString
            val uniqueTeacherId = call.parameters[UniqueTeacherId] ?: emptyString
            val result = teacherStudentRepository.deleteTeacherStudent(TeacherStudent(uniqueTeacherId, uniqueStudentId))
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }
    }
}
