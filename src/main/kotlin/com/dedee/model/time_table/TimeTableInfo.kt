package com.dedee.model.time_table

@kotlinx.serialization.Serializable
data class TimeTableInfo(
    val uniqueTimeTableId: String?,
    val uniqueSubjectId: String?,
    val uniqueClassId: String?,
    val uniqueTeacherId: String?,
    val uniqueTermName: String?,
    val timeTableType: String?,
    val days: String?,
    val startTime: String?,
    val endTime: String?,
)
