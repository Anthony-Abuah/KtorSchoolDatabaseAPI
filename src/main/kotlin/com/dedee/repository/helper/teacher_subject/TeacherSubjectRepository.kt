package com.dedee.repository.helper.teacher_subject

import com.dedee.model.helper_tables.TeacherSubject
import com.dedee.model.subject.SubjectInfo
import com.dedee.model.teacher.TeacherInfo
import util.ResponseFeedback


interface TeacherSubjectRepository {
    suspend fun getAllTeacherSubjects(): ResponseFeedback<List<TeacherSubject>?>

    suspend fun getAllTeachersOfThisSubject(uniqueSubjectId: String): ResponseFeedback<List<TeacherInfo>?>

    suspend fun getAllSubjectsOfThisTeacher(uniqueTeacherId: String): ResponseFeedback<List<SubjectInfo>?>

    suspend fun addTeacherSubject(teacherSubject: TeacherSubject): ResponseFeedback<TeacherSubject?>

    suspend fun updateTeacherSubject(uniqueTeacherId: String, uniqueSubjectId: String, teacherSubject: TeacherSubject): ResponseFeedback<TeacherSubject?>

    suspend fun deleteTeacherSubject(teacherSubject: TeacherSubject): ResponseFeedback<String>

    
}



