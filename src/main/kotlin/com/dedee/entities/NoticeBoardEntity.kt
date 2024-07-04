package com.dedee.entities

import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar
import util.Constants.ScoreTable
import util.DatabaseEntityFields.Message
import util.DatabaseEntityFields.NoticeTime
import util.DatabaseEntityFields.Priority
import util.DatabaseEntityFields.Title
import util.DatabaseEntityFields.UniqueNoticeId
import util.DatabaseEntityFields.UniqueTermName

object NoticeBoardEntity: Table<Nothing>(ScoreTable) {
    val uniqueNoticeId = varchar(UniqueNoticeId).primaryKey()
    val title = varchar(Title)
    val message = varchar(Message)
    val priority = varchar(Priority)
    val uniqueTermName = varchar(UniqueTermName)
    val noticeTime = int(NoticeTime)


}