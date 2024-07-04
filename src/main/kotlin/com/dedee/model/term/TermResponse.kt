package com.dedee.model.term

@kotlinx.serialization.Serializable
data class TermResponse(
    val termName: String?,
    val uniqueTermName: String?,
    val year: Int?,
    val startDate: Int?,
    val endDate: Int?,
    val numberOfDays: Int?,
)
