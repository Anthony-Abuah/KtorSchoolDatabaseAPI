package com.dedee.repository.helper.student_subject

import com.dedee.model.helper_tables.StudentSubject
import com.dedee.model.student.StudentInfo
import com.dedee.model.subject.SubjectInfo
import util.ResponseFeedback


interface StudentSubjectRepository {
    suspend fun getAllStudentSubjects(): ResponseFeedback<List<StudentSubject>?>

    suspend fun getAllStudentsOfThisSubject(uniqueSubjectId: String): ResponseFeedback<List<StudentInfo>?>

    suspend fun getAllSubjectsOfThisStudent(uniqueStudentId: String): ResponseFeedback<List<SubjectInfo>?>

    suspend fun addStudentSubject(studentSubject: StudentSubject): ResponseFeedback<StudentSubject?>

    suspend fun updateStudentSubject(uniqueStudentId: String, uniqueSubjectId: String, studentSubject: StudentSubject): ResponseFeedback<StudentSubject?>

    suspend fun deleteStudentSubject(studentSubject: StudentSubject): ResponseFeedback<String>

    
}



