package com.dedee.repository.notice_board

import com.dedee.model.notice_board.NoticeBoardRequest
import com.dedee.model.notice_board.NoticeBoardResponse
import com.dedee.service.notice_board.NoticeBoardService
import util.Constants.emptyString
import util.Functions.uniqueNoticeIdGenerator
import util.ResponseFeedback

class NoticeBoardRepositoryImpl(
    private val noticeBoardService: NoticeBoardService,
) : NoticeBoardRepository{
    override suspend fun getAllNotices(): ResponseFeedback<List<NoticeBoardResponse>?> {
        val noticeBoardResponse = noticeBoardService.getAllNotices()
        return if (noticeBoardResponse.isNullOrEmpty()) {
            ResponseFeedback(
                data = null,
                message = "Could not fetch all noticeBoards",
                success = false
            )
        }else{
            ResponseFeedback(
                data = noticeBoardResponse,
                message = "All noticeBoards successfully loaded",
                success = true
            )
        }
    }

    override suspend fun addNotice(notice: NoticeBoardRequest): ResponseFeedback<NoticeBoardResponse?> {
        val title = notice.title ?: emptyString
        val uniqueNoticeId = uniqueNoticeIdGenerator(title)
        return if (uniqueNoticeIdExists(uniqueNoticeId)){
            ResponseFeedback(
                data = null,
                message = "Cannot add time table because noticeBoard already exists",
                success = false
            )
        }else{
            val registerResponse = noticeBoardService.addNotice(notice, uniqueNoticeId)
            if (registerResponse < 1 ) {
                ResponseFeedback(
                    data = null,
                    message = "Unable to add noticeBoard",
                    success = false
                )
            }else{
                val registeredNotice = noticeBoardService.getNotice(uniqueNoticeId)?.first()
                ResponseFeedback(
                    data = registeredNotice,
                    message = "Notice successfully added",
                    success = true
                )
            }
        }
    }

    override suspend fun getNotice(
        uniqueNoticeId: String,
    ): ResponseFeedback<List<NoticeBoardResponse>?> {
        val response = noticeBoardService.getNotice(uniqueNoticeId)
        return if (response.isNullOrEmpty()){
            ResponseFeedback(
                data = null,
                message = "No noticeBoard matches the given criteria",
                success = false
            )
        }else{
            ResponseFeedback(
                data = response,
                message = "Notices fetched successfully",
                success = true
            )
        }
    }

    override suspend fun deleteNotice(uniqueNoticeId: String): ResponseFeedback<String> {
        val response =  noticeBoardService.deleteNotice(uniqueNoticeId) ?: -1
        return if (response < 1){
            ResponseFeedback(
                data = "Unable to delete noticeBoard",
                message = "Unknown Error",
                success = false
            )
        }else{
            ResponseFeedback(
                data = "Notice deleted successfully",
                message = "Notice deleted successfully",
                success = true
            )
        }

    }

    override suspend fun updateNotice(
        uniqueNoticeId: String,
        updatedNotice: NoticeBoardRequest,
    ): ResponseFeedback<NoticeBoardResponse?> {
        val response = noticeBoardService.updateNotice(uniqueNoticeId, updatedNotice)
        return if (response == null){
            ResponseFeedback(
                data = null,
                message = "Unable to update noticeBoard",
                success = false
            )
        }else{
            ResponseFeedback(
                data = response,
                message = "Notice updated successfully",
                success = true
            )
        }
    }


    private suspend fun uniqueNoticeIdExists(uniqueNoticeId: String): Boolean {
        return !(noticeBoardService.getNotice(uniqueNoticeId).isNullOrEmpty())
    }

}