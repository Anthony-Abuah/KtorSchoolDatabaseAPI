package com.dedee.service.helper.teacher_subject

import com.dedee.model.helper_tables.TeacherSubject
import com.dedee.model.subject.SubjectInfo
import com.dedee.model.teacher.TeacherInfo

interface TeacherSubjectService {

    suspend fun getAllTeacherSubjects(): List<TeacherSubject>?

    suspend fun getAllTeachersOfThisSubject(uniqueSubjectId: String): List<TeacherInfo>?

    suspend fun getAllSubjectsOfThisTeacher(uniqueTeacherId: String): List<SubjectInfo>?

    suspend fun addTeacherSubject(teacherSubject: TeacherSubject): TeacherSubject?

    suspend fun updateTeacherSubject(uniqueTeacherId: String, uniqueSubjectId: String, teacherSubject: TeacherSubject): TeacherSubject?

    suspend fun deleteTeacherSubject(teacherSubject: TeacherSubject): Int?

}