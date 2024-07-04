package com.dedee.repository.helper.student_subject

import com.dedee.model.helper_tables.StudentSubject
import com.dedee.model.student.StudentInfo
import com.dedee.model.subject.SubjectInfo
import com.dedee.service.helper.student_subject.StudentSubjectService
import util.ResponseFeedback

class StudentSubjectRepositoryImpl(
    private val studentSubjectService: StudentSubjectService,
) : StudentSubjectRepository{
    override suspend fun getAllStudentSubjects(): ResponseFeedback<List<StudentSubject>?> {
        val result = studentSubjectService.getAllStudentSubjects()
        return if (result != null){
            ResponseFeedback(
                data = result,
                message = "Data has been fetched successfully",
                success = true
            )
        }
        else{
            ResponseFeedback(
                data = null,
                message = "Unable to load data",
                success = false
            )
        }
    }

    override suspend fun getAllStudentsOfThisSubject(uniqueSubjectId: String): ResponseFeedback<List<StudentInfo>?> {
        val studentInfo = studentSubjectService.getAllStudentsOfThisSubject(uniqueSubjectId)
        return if (studentInfo != null){
            ResponseFeedback(
                data = studentInfo,
                message = "Data has been fetched successfully",
                success = true
            )
        }
        else{
            ResponseFeedback(
                data = null,
                message = "Unable to load data",
                success = false
            )
        }
    }

    override suspend fun getAllSubjectsOfThisStudent(uniqueStudentId: String): ResponseFeedback<List<SubjectInfo>?> {
        val parentInfo = studentSubjectService.getAllSubjectsOfThisStudent(uniqueStudentId)
        return if (parentInfo != null){
            ResponseFeedback(
                data = parentInfo,
                message = "Data has been fetched successfully",
                success = true
            )
        }
        else{
            ResponseFeedback(
                data = null,
                message = "Unable to load data",
                success = false
            )
        }
    }

    override suspend fun addStudentSubject(studentSubject: StudentSubject): ResponseFeedback<StudentSubject?> {
        val _studentSubject = studentSubjectService.addStudentSubject(studentSubject)
        return if (_studentSubject != null){
            ResponseFeedback(
                data = _studentSubject,
                message = "Data has been fetched successfully",
                success = true
            )
        }
        else{
            ResponseFeedback(
                data = null,
                message = "Unable to load data",
                success = false
            )
        }
    }

    override suspend fun updateStudentSubject(
        uniqueStudentId: String,
        uniqueSubjectId: String,
        studentSubject: StudentSubject,
    ): ResponseFeedback<StudentSubject?> {
        val _updatedStudentSubject = studentSubjectService.updateStudentSubject(uniqueStudentId, uniqueSubjectId, studentSubject)
        return if (_updatedStudentSubject != null){
            ResponseFeedback(
                data = _updatedStudentSubject,
                message = "Data has been updated successfully",
                success = true
            )
        }
        else{
            ResponseFeedback(
                data = null,
                message = "Unable to update data",
                success = false
            )
        }
    }

    override suspend fun deleteStudentSubject(studentSubject: StudentSubject): ResponseFeedback<String> {
        val deletedRows = studentSubjectService.deleteStudentSubject(studentSubject)
        return if (deletedRows != null && deletedRows  > 0){
            ResponseFeedback(
                data = "Data deleted successfully",
                message = "Data deleted successfully",
                success = true
            )
        }
        else{
            ResponseFeedback(
                data = null,
                message = "Unable to delete data",
                success = false
            )
        }
    }
}