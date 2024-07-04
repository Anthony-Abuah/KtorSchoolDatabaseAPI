package com.dedee.repository.helper.teacher_subject

import com.dedee.model.helper_tables.TeacherSubject
import com.dedee.model.subject.SubjectInfo
import com.dedee.model.teacher.TeacherInfo
import com.dedee.service.helper.teacher_subject.TeacherSubjectService
import util.ResponseFeedback

class TeacherSubjectRepositoryImpl(
    private val teacherSubjectService: TeacherSubjectService,
) : TeacherSubjectRepository{
    override suspend fun getAllTeacherSubjects(): ResponseFeedback<List<TeacherSubject>?> {
        val result = teacherSubjectService.getAllTeacherSubjects()
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

    override suspend fun getAllTeachersOfThisSubject(uniqueSubjectId: String): ResponseFeedback<List<TeacherInfo>?> {
        val teacherInfo = teacherSubjectService.getAllTeachersOfThisSubject(uniqueSubjectId)
        return if (teacherInfo != null){
            ResponseFeedback(
                data = teacherInfo,
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

    override suspend fun getAllSubjectsOfThisTeacher(uniqueTeacherId: String): ResponseFeedback<List<SubjectInfo>?> {
        val parentInfo = teacherSubjectService.getAllSubjectsOfThisTeacher(uniqueTeacherId)
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

    override suspend fun addTeacherSubject(teacherSubject: TeacherSubject): ResponseFeedback<TeacherSubject?> {
        val _teacherSubject = teacherSubjectService.addTeacherSubject(teacherSubject)
        return if (_teacherSubject != null){
            ResponseFeedback(
                data = _teacherSubject,
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

    override suspend fun updateTeacherSubject(
        uniqueTeacherId: String,
        uniqueSubjectId: String,
        teacherSubject: TeacherSubject,
    ): ResponseFeedback<TeacherSubject?> {
        val _updatedTeacherSubject = teacherSubjectService.updateTeacherSubject(uniqueTeacherId, uniqueSubjectId, teacherSubject)
        return if (_updatedTeacherSubject != null){
            ResponseFeedback(
                data = _updatedTeacherSubject,
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

    override suspend fun deleteTeacherSubject(teacherSubject: TeacherSubject): ResponseFeedback<String> {
        val deletedRows = teacherSubjectService.deleteTeacherSubject(teacherSubject)
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