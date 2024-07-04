package com.dedee.route

import com.dedee.model.helper_tables.TeacherSubject
import com.dedee.repository.helper.teacher_subject.TeacherSubjectRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import util.Constants.emptyString
import util.DatabaseEntityFields.UniqueSubjectId
import util.DatabaseEntityFields.UniqueTeacherId
import util.Routes.addTeacherSubject
import util.Routes.deleteTeacherSubject
import util.Routes.getAllTeacherSubjects
import util.Routes.getSubjectsOfThisTeacher
import util.Routes.getTeachersOfThisSubject
import util.Routes.updateTeacherSubject
import util.Routes.updatedSubjectId
import util.Routes.updatedTeacherId

fun Application.teacherSubjectRoute(teacherSubjectRepository: TeacherSubjectRepository) {
    routing {
        get(getAllTeacherSubjects) {
            val result = teacherSubjectRepository.getAllTeacherSubjects()
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }
        get(getTeachersOfThisSubject) {
            val uniqueSubjectId = call.parameters[UniqueSubjectId] ?: emptyString
            val result = teacherSubjectRepository.getAllTeachersOfThisSubject(uniqueSubjectId)
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }

        get(getSubjectsOfThisTeacher) {
            val uniqueSubjectId = call.parameters[UniqueTeacherId] ?: emptyString
            val result = teacherSubjectRepository.getAllSubjectsOfThisTeacher(uniqueSubjectId)
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }

        post (addTeacherSubject){
            val uniqueSubjectId = call.parameters[UniqueSubjectId] ?: emptyString
            val uniqueTeacherId = call.parameters[UniqueSubjectId] ?: emptyString
            val result = teacherSubjectRepository.addTeacherSubject(TeacherSubject(uniqueTeacherId, uniqueSubjectId))
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }

        put(updateTeacherSubject){
            val uniqueSubjectId = call.parameters[UniqueSubjectId] ?: emptyString
            val uniqueTeacherId = call.parameters[UniqueTeacherId] ?: emptyString
            val thisUpdatedTeacherId = call.parameters[updatedTeacherId] ?: emptyString
            val thisUpdatedSubjectId = call.parameters[updatedSubjectId] ?: emptyString
            val result = teacherSubjectRepository.updateTeacherSubject(uniqueTeacherId, uniqueSubjectId, TeacherSubject(thisUpdatedTeacherId,thisUpdatedSubjectId))
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }


        delete (deleteTeacherSubject){
            val uniqueSubjectId = call.parameters[UniqueSubjectId] ?: emptyString
            val uniqueTeacherId = call.parameters[UniqueTeacherId] ?: emptyString
            val result = teacherSubjectRepository.deleteTeacherSubject(TeacherSubject(uniqueTeacherId, uniqueSubjectId))
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }
    }
}
