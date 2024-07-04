package com.dedee.repository.notice_board

import com.dedee.model.notice_board.NoticeBoardRequest
import com.dedee.model.notice_board.NoticeBoardResponse
import util.ResponseFeedback


interface NoticeBoardRepository {
    suspend fun getAllNotices(): ResponseFeedback<List<NoticeBoardResponse>?>

    suspend fun addNotice(notice: NoticeBoardRequest): ResponseFeedback<NoticeBoardResponse?>

    suspend fun getNotice(uniqueNoticeId: String): ResponseFeedback<List<NoticeBoardResponse>?>

    suspend fun deleteNotice(uniqueNoticeId: String): ResponseFeedback<String>

    suspend fun updateNotice(uniqueNoticeId: String, updatedNotice: NoticeBoardRequest): ResponseFeedback<NoticeBoardResponse?>

    
}



