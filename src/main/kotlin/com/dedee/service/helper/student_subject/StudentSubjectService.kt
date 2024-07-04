package com.dedee.service.helper.student_subject

import com.dedee.model.helper_tables.StudentSubject
import com.dedee.model.student.StudentInfo
import com.dedee.model.subject.SubjectInfo

interface StudentSubjectService {

    suspend fun getAllStudentSubjects(): List<StudentSubject>?

    suspend fun getAllStudentsOfThisSubject(uniqueSubjectId: String): List<StudentInfo>?

    suspend fun getAllSubjectsOfThisStudent(uniqueStudentId: String): List<SubjectInfo>?

    suspend fun addStudentSubject(studentSubject: StudentSubject): StudentSubject?

    suspend fun updateStudentSubject(uniqueStudentId: String, uniqueSubjectId: String, studentSubject: StudentSubject): StudentSubject?

    suspend fun deleteStudentSubject(studentSubject: StudentSubject): Int?

}