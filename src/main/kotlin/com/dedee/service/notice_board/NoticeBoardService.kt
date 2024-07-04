package com.dedee.service.notice_board

import com.dedee.model.notice_board.NoticeBoardRequest
import com.dedee.model.notice_board.NoticeBoardResponse

interface NoticeBoardService {

    suspend fun getAllNotices(): List<NoticeBoardResponse>?

    suspend fun addNotice(notice: NoticeBoardRequest, uniqueNoticeId: String): Int

    suspend fun getNotice(uniqueNoticeId: String): List<NoticeBoardResponse>?

    suspend fun updateNotice(uniqueNoticeId: String, updatedNotice: NoticeBoardRequest): NoticeBoardResponse?

    suspend fun deleteNotice(uniqueNoticeId: String): Int?

}