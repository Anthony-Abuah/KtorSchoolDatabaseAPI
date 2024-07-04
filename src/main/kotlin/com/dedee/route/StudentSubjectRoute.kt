package com.dedee.route

import com.dedee.model.helper_tables.StudentSubject
import com.dedee.repository.helper.student_subject.StudentSubjectRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import util.Constants.emptyString
import util.DatabaseEntityFields.UniqueSubjectId
import util.DatabaseEntityFields.UniqueStudentId
import util.Routes.addStudentSubject
import util.Routes.deleteStudentSubject
import util.Routes.getAllStudentSubjects
import util.Routes.getSubjectsOfThisStudent
import util.Routes.getStudentsOfThisSubject
import util.Routes.updateStudentSubject
import util.Routes.updatedSubjectId
import util.Routes.updatedStudentId

fun Application.studentSubjectRoute(studentSubjectRepository: StudentSubjectRepository) {
    routing {
        get(getAllStudentSubjects) {
            val result = studentSubjectRepository.getAllStudentSubjects()
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }
        get(getStudentsOfThisSubject) {
            val uniqueSubjectId = call.parameters[UniqueSubjectId] ?: emptyString
            val result = studentSubjectRepository.getAllStudentsOfThisSubject(uniqueSubjectId)
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }

        get(getSubjectsOfThisStudent) {
            val uniqueSubjectId = call.parameters[UniqueStudentId] ?: emptyString
            val result = studentSubjectRepository.getAllSubjectsOfThisStudent(uniqueSubjectId)
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }

        post (addStudentSubject){
            val uniqueSubjectId = call.parameters[UniqueSubjectId] ?: emptyString
            val uniqueStudentId = call.parameters[UniqueSubjectId] ?: emptyString
            val result = studentSubjectRepository.addStudentSubject(StudentSubject(uniqueStudentId, uniqueSubjectId))
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }

        put(updateStudentSubject){
            val uniqueSubjectId = call.parameters[UniqueSubjectId] ?: emptyString
            val uniqueStudentId = call.parameters[UniqueStudentId] ?: emptyString
            val thisUpdatedStudentId = call.parameters[updatedStudentId] ?: emptyString
            val thisUpdatedSubjectId = call.parameters[updatedSubjectId] ?: emptyString
            val result = studentSubjectRepository.updateStudentSubject(uniqueStudentId, uniqueSubjectId, StudentSubject(thisUpdatedStudentId,thisUpdatedSubjectId))
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }


        delete (deleteStudentSubject){
            val uniqueSubjectId = call.parameters[UniqueSubjectId] ?: emptyString
            val uniqueStudentId = call.parameters[UniqueStudentId] ?: emptyString
            val result = studentSubjectRepository.deleteStudentSubject(StudentSubject(uniqueStudentId, uniqueSubjectId))
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }
    }
}
