package com.dedee.service.notice_board

import com.dedee.entities.NoticeBoardEntity
import com.dedee.model.notice_board.NoticeBoardRequest
import com.dedee.model.notice_board.NoticeBoardResponse
import org.ktorm.database.Database
import org.ktorm.dsl.*
import util.Constants.emptyString

class NoticeBoardServiceImpl(
    private val db: Database,
) : NoticeBoardService{
    override suspend fun getAllNotices(): List<NoticeBoardResponse>? {
        val timeTable = db.from(NoticeBoardEntity).select()
            .map {
                val uniqueNoticeId = it[NoticeBoardEntity.uniqueNoticeId] ?: emptyString
                val title = it[NoticeBoardEntity.title] ?: emptyString
                val message = it[NoticeBoardEntity.message] ?: emptyString
                val noticeTime = it[NoticeBoardEntity.noticeTime] ?: 0
                val priority = it[NoticeBoardEntity.priority] ?: emptyString
                val uniqueTermName = it[NoticeBoardEntity.uniqueTermName] ?: emptyString
                NoticeBoardResponse(uniqueNoticeId, title, message, priority, noticeTime, uniqueTermName)
            }
        return timeTable.ifEmpty { null }
    }

    override suspend fun addNotice(notice: NoticeBoardRequest, uniqueNoticeId: String): Int {
        return db.insert(NoticeBoardEntity){
            set(it.uniqueNoticeId, uniqueNoticeId)
            set(it.title, notice.title)
            set(it.message, notice.message)
            set(it.uniqueTermName, notice.uniqueTermName)
            set(it.noticeTime, notice.noticeTime)
            set(it.priority, notice.priority)
        }
    }

    override suspend fun getNotice(uniqueNoticeId: String): List<NoticeBoardResponse> {
        return db.from(NoticeBoardEntity)
            .select()
            .where { NoticeBoardEntity.uniqueNoticeId eq uniqueNoticeId }
            .map {
                val title = it[NoticeBoardEntity.title] ?: emptyString
                val message = it[NoticeBoardEntity.message] ?: emptyString
                val noticeTime = it[NoticeBoardEntity.noticeTime] ?: 0
                val priority = it[NoticeBoardEntity.priority] ?: emptyString
                val uniqueTermName = it[NoticeBoardEntity.uniqueTermName] ?: emptyString
                NoticeBoardResponse(uniqueNoticeId, title, message, priority, noticeTime, uniqueTermName)
            }


    }

    override suspend fun updateNotice(uniqueNoticeId: String, updatedNotice: NoticeBoardRequest): NoticeBoardResponse? {
        val rowsAffected = db.update(NoticeBoardEntity){
            set(it.title, updatedNotice.title)
            set(it.message, updatedNotice.message)
            set(it.uniqueTermName, updatedNotice.uniqueTermName)
            set(it.noticeTime, updatedNotice.noticeTime)
            set(it.priority, updatedNotice.priority)
            where { it.uniqueNoticeId eq uniqueNoticeId }
        }
        return if (rowsAffected < 1){
            null
        }else {
            db.from(NoticeBoardEntity)
                .select()
                .where { NoticeBoardEntity.uniqueNoticeId eq uniqueNoticeId }
                .map {
                    val title = it[NoticeBoardEntity.title] ?: emptyString
                    val message = it[NoticeBoardEntity.message] ?: emptyString
                    val noticeTime = it[NoticeBoardEntity.noticeTime] ?: 0
                    val priority = it[NoticeBoardEntity.priority] ?: emptyString
                    val uniqueTermName = it[NoticeBoardEntity.uniqueTermName] ?: emptyString
                    NoticeBoardResponse(uniqueNoticeId, title, message, priority, noticeTime, uniqueTermName)
                }.firstOrNull()
        }
    }

    override suspend fun deleteNotice(uniqueNoticeId: String): Int {
        return db.delete(NoticeBoardEntity) {
            NoticeBoardEntity.uniqueNoticeId eq uniqueNoticeId
        }
    }

}