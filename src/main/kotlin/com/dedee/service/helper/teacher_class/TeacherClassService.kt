package com.dedee.service.helper.teacher_class

import com.dedee.model.helper_tables.TeacherClass
import com.dedee.model.school_class.SchoolClassInfo
import com.dedee.model.teacher.TeacherInfo

interface TeacherClassService {

    suspend fun getAllTeacherClasses(): List<TeacherClass>?

    suspend fun getAllTeachersOfThisClass(uniqueClassId: String): List<TeacherInfo>?

    suspend fun getAllClassesOfThisTeacher(uniqueTeacherId: String): List<SchoolClassInfo>?

    suspend fun addTeacherClass(teacherClass: TeacherClass): TeacherClass?

    suspend fun updateTeacherClass(uniqueTeacherId: String, uniqueClassId: String, teacherClass: TeacherClass): TeacherClass?

    suspend fun deleteTeacherClass(teacherClass: TeacherClass): Int?

}