package com.dedee.model.notice_board

@kotlinx.serialization.Serializable
data class NoticeBoardRequest(
    val title: String?,
    val message: String?,
    val priority: String?,
    val noticeTime: Int?,
    val uniqueTermName: String?,
)
