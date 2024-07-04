package com.dedee.route

import com.dedee.model.helper_tables.TeacherClass
import com.dedee.repository.helper.teacher_class.TeacherClassRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import util.Constants.emptyString
import util.DatabaseEntityFields.UniqueClassId
import util.DatabaseEntityFields.UniqueTeacherId
import util.Routes.addTeacherClass
import util.Routes.deleteTeacherClass
import util.Routes.getAllTeacherClasses
import util.Routes.getClassesOfThisTeacher
import util.Routes.getTeachersOfThisClass
import util.Routes.updateTeacherClass
import util.Routes.updatedClassId
import util.Routes.updatedTeacherId

fun Application.teacherClassRoute(teacherClassRepository: TeacherClassRepository) {
    routing {
        get(getAllTeacherClasses) {
            val result = teacherClassRepository.getAllTeacherClasses()
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }
        get(getTeachersOfThisClass) {
            val uniqueClassId = call.parameters[UniqueClassId] ?: emptyString
            val result = teacherClassRepository.getAllTeachersOfThisClass(uniqueClassId)
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }

        get(getClassesOfThisTeacher) {
            val uniqueClassId = call.parameters[UniqueTeacherId] ?: emptyString
            val result = teacherClassRepository.getAllClassesOfThisTeacher(uniqueClassId)
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }

        post (addTeacherClass){
            val uniqueClassId = call.parameters[UniqueClassId] ?: emptyString
            val uniqueTeacherId = call.parameters[UniqueClassId] ?: emptyString
            val result = teacherClassRepository.addTeacherClass(TeacherClass(uniqueTeacherId, uniqueClassId))
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }

        put(updateTeacherClass){
            val uniqueClassId = call.parameters[UniqueClassId] ?: emptyString
            val uniqueTeacherId = call.parameters[UniqueTeacherId] ?: emptyString
            val thisUpdatedTeacherId = call.parameters[updatedTeacherId] ?: emptyString
            val thisUpdatedClassId = call.parameters[updatedClassId] ?: emptyString
            val result = teacherClassRepository.updateTeacherClass(uniqueTeacherId, uniqueClassId, TeacherClass(thisUpdatedTeacherId,thisUpdatedClassId))
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }


        delete (deleteTeacherClass){
            val uniqueClassId = call.parameters[UniqueClassId] ?: emptyString
            val uniqueTeacherId = call.parameters[UniqueTeacherId] ?: emptyString
            val result = teacherClassRepository.deleteTeacherClass(TeacherClass(uniqueTeacherId, uniqueClassId))
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }
    }
}
