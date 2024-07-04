package com.dedee.repository.helper.teacher_student

import com.dedee.model.helper_tables.TeacherStudent
import com.dedee.model.student.StudentInfo
import com.dedee.model.teacher.TeacherInfo
import com.dedee.service.helper.teacher_student.TeacherStudentService
import util.ResponseFeedback

class TeacherStudentRepositoryImpl(
    private val teacherStudentService: TeacherStudentService,
) : TeacherStudentRepository{
    override suspend fun getAllTeacherStudents(): ResponseFeedback<List<TeacherStudent>?> {
        val result = teacherStudentService.getAllTeacherStudents()
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

    override suspend fun getAllTeachersOfThisStudent(uniqueStudentId: String): ResponseFeedback<List<TeacherInfo>?> {
        val teacherInfo = teacherStudentService.getAllTeachersOfThisStudent(uniqueStudentId)
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

    override suspend fun getAllStudentsOfThisTeacher(uniqueTeacherId: String): ResponseFeedback<List<StudentInfo>?> {
        val parentInfo = teacherStudentService.getAllStudentsOfThisTeacher(uniqueTeacherId)
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

    override suspend fun addTeacherStudent(teacherStudent: TeacherStudent): ResponseFeedback<TeacherStudent?> {
        val _teacherStudent = teacherStudentService.addTeacherStudent(teacherStudent)
        return if (_teacherStudent != null){
            ResponseFeedback(
                data = _teacherStudent,
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

    override suspend fun updateTeacherStudent(
        uniqueTeacherId: String,
        uniqueStudentId: String,
        teacherStudent: TeacherStudent,
    ): ResponseFeedback<TeacherStudent?> {
        val _updatedTeacherStudent = teacherStudentService.updateTeacherStudent(uniqueTeacherId, uniqueStudentId, teacherStudent)
        return if (_updatedTeacherStudent != null){
            ResponseFeedback(
                data = _updatedTeacherStudent,
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

    override suspend fun deleteTeacherStudent(teacherStudent: TeacherStudent): ResponseFeedback<String> {
        val deletedRows = teacherStudentService.deleteTeacherStudent(teacherStudent)
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