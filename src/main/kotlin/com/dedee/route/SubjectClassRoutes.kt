package com.dedee.route

import com.dedee.model.helper_tables.SubjectClass
import com.dedee.repository.helper.subject_class.SubjectClassRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import util.Constants.emptyString
import util.DatabaseEntityFields.UniqueClassId
import util.DatabaseEntityFields.UniqueSubjectId
import util.Routes.addSubjectClass
import util.Routes.deleteSubjectClass
import util.Routes.getAllSubjectClasses
import util.Routes.getClassesOfThisSubject
import util.Routes.getSubjectsOfThisClass
import util.Routes.updateSubjectClass
import util.Routes.updatedClassId
import util.Routes.updatedSubjectId

fun Application.subjectClassRoute(subjectClassRepository: SubjectClassRepository) {
    routing {
        get(getAllSubjectClasses) {
            val result = subjectClassRepository.getAllSubjectClasses()
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }
        get(getSubjectsOfThisClass) {
            val uniqueClassId = call.parameters[UniqueClassId] ?: emptyString
            val result = subjectClassRepository.getAllSubjectsOfThisClass(uniqueClassId)
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }

        get(getClassesOfThisSubject) {
            val uniqueClassId = call.parameters[UniqueSubjectId] ?: emptyString
            val result = subjectClassRepository.getAllClassesOfThisSubject(uniqueClassId)
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }

        post (addSubjectClass){
            val uniqueClassId = call.parameters[UniqueClassId] ?: emptyString
            val uniqueSubjectId = call.parameters[UniqueClassId] ?: emptyString
            val result = subjectClassRepository.addSubjectClass(SubjectClass(uniqueSubjectId, uniqueClassId))
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }

        put(updateSubjectClass){
            val uniqueClassId = call.parameters[UniqueClassId] ?: emptyString
            val uniqueSubjectId = call.parameters[UniqueSubjectId] ?: emptyString
            val thisUpdatedSubjectId = call.parameters[updatedSubjectId] ?: emptyString
            val thisUpdatedClassId = call.parameters[updatedClassId] ?: emptyString
            val result = subjectClassRepository.updateSubjectClass(uniqueSubjectId, uniqueClassId, SubjectClass(thisUpdatedSubjectId,thisUpdatedClassId))
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }


        delete (deleteSubjectClass){
            val uniqueClassId = call.parameters[UniqueClassId] ?: emptyString
            val uniqueSubjectId = call.parameters[UniqueSubjectId] ?: emptyString
            val result = subjectClassRepository.deleteSubjectClass(SubjectClass(uniqueSubjectId, uniqueClassId))
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }
    }
}
