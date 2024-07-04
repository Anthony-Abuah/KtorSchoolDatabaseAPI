package com.dedee.route

import com.dedee.model.helper_tables.StudentParent
import com.dedee.repository.helper.student_parent.StudentParentRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import util.Constants.emptyString
import util.DatabaseEntityFields.ParentUsername
import util.DatabaseEntityFields.UniqueStudentId
import util.Routes.addStudentParent
import util.Routes.deleteStudentParent
import util.Routes.getAllStudentParents
import util.Routes.getParentsOfThisStudent
import util.Routes.getStudentsOfThisParent
import util.Routes.updateStudentParent
import util.Routes.updatedParentUsername
import util.Routes.updatedUniqueStudentId

fun Application.studentParentRoutes(studentParentRepository: StudentParentRepository) {
    routing {
        get(getAllStudentParents) {
            val result = studentParentRepository.getAllStudentParents()
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }
        get(getStudentsOfThisParent) {
            val parentUsername = call.parameters[ParentUsername] ?: emptyString
            val result = studentParentRepository.getAllStudentsOfThisParent(parentUsername)
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }

        get(getParentsOfThisStudent) {
            val uniqueStudentId = call.parameters[UniqueStudentId] ?: emptyString
            val result = studentParentRepository.getAllParentsOfThisStudent(uniqueStudentId)
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }

        post (addStudentParent){
            val uniqueStudentId = call.parameters[UniqueStudentId] ?: emptyString
            val parentUsername = call.parameters[ParentUsername] ?: emptyString
            val result = studentParentRepository.addStudentParent(StudentParent(uniqueStudentId, parentUsername))
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }

        put(updateStudentParent){
            val uniqueStudentId = call.parameters[UniqueStudentId] ?: emptyString
            val parentUsername = call.parameters[ParentUsername] ?: emptyString
            val thisUpdatedUniqueStudentId = call.parameters[updatedUniqueStudentId] ?: emptyString
            val thisUpdatedParentUsername = call.parameters[updatedParentUsername] ?: emptyString
            val result = studentParentRepository.updateStudentParent(uniqueStudentId, parentUsername, StudentParent(thisUpdatedUniqueStudentId,thisUpdatedParentUsername))
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }


        delete (deleteStudentParent){
            val uniqueStudentId = call.parameters[UniqueStudentId] ?: emptyString
            val parentUsername = call.parameters[ParentUsername] ?: emptyString
            val result = studentParentRepository.deleteStudentParent(StudentParent(uniqueStudentId, parentUsername))
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }
    }
}
