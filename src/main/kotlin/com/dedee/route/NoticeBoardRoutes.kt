package com.dedee.route

import com.dedee.model.notice_board.NoticeBoardRequest
import com.dedee.repository.notice_board.NoticeBoardRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import util.Constants.emptyString
import util.DatabaseEntityFields.UniqueNoticeId
import util.Routes.deleteNotice
import util.Routes.getAllNotices
import util.Routes.getNotice
import util.Routes.registerNotice
import util.Routes.updateNotice

fun Application.noticeBoardRoutes(noticeBoardRepository: NoticeBoardRepository) {
    routing {
        get(getAllNotices) {
            val result = noticeBoardRepository.getAllNotices()
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }
        get(getNotice) {
            val uniqueNoticeId = call.parameters[UniqueNoticeId] ?: emptyString
            val result = noticeBoardRepository.getNotice(uniqueNoticeId)
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }

        post (registerNotice){
            val noticeRequest = call.receive<NoticeBoardRequest>()
            val result = noticeBoardRepository.addNotice(noticeRequest)
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }

        put(updateNotice){
            val uniqueNoticeId = call.parameters[UniqueNoticeId] ?: emptyString
            val noticeRequest = call.receive<NoticeBoardRequest>()
            val result = noticeBoardRepository.updateNotice(uniqueNoticeId, noticeRequest)
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }


        delete (deleteNotice){
            val uniqueNoticeId = call.parameters[UniqueNoticeId] ?: emptyString
            val result = noticeBoardRepository.deleteNotice(uniqueNoticeId)
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }
    }
}
