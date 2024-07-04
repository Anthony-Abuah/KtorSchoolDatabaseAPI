package com.dedee.repository.helper.teacher_class

import com.dedee.model.helper_tables.TeacherClass
import com.dedee.model.school_class.SchoolClassInfo
import com.dedee.model.teacher.TeacherInfo
import util.ResponseFeedback


interface TeacherClassRepository {
    suspend fun getAllTeacherClasses(): ResponseFeedback<List<TeacherClass>?>

    suspend fun getAllTeachersOfThisClass(uniqueClassId: String): ResponseFeedback<List<TeacherInfo>?>

    suspend fun getAllClassesOfThisTeacher(uniqueTeacherId: String): ResponseFeedback<List<SchoolClassInfo>?>

    suspend fun addTeacherClass(teacherClass: TeacherClass): ResponseFeedback<TeacherClass?>

    suspend fun updateTeacherClass(uniqueTeacherId: String, uniqueClassId: String, teacherClass: TeacherClass): ResponseFeedback<TeacherClass?>

    suspend fun deleteTeacherClass(teacherClass: TeacherClass): ResponseFeedback<String>

    
}



