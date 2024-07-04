package com.dedee.repository.helper.teacher_class

import com.dedee.model.helper_tables.TeacherClass
import com.dedee.model.school_class.SchoolClassInfo
import com.dedee.model.teacher.TeacherInfo
import com.dedee.service.helper.teacher_class.TeacherClassService
import util.ResponseFeedback

class TeacherClassRepositoryImpl(
    private val teacherClassService: TeacherClassService,
) : TeacherClassRepository{
    override suspend fun getAllTeacherClasses(): ResponseFeedback<List<TeacherClass>?> {
        val result = teacherClassService.getAllTeacherClasses()
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

    override suspend fun getAllTeachersOfThisClass(uniqueClassId: String): ResponseFeedback<List<TeacherInfo>?> {
        val teacherInfo = teacherClassService.getAllTeachersOfThisClass(uniqueClassId)
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

    override suspend fun getAllClassesOfThisTeacher(uniqueTeacherId: String): ResponseFeedback<List<SchoolClassInfo>?> {
        val parentInfo = teacherClassService.getAllClassesOfThisTeacher(uniqueTeacherId)
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

    override suspend fun addTeacherClass(teacherClass: TeacherClass): ResponseFeedback<TeacherClass?> {
        val _teacherClass = teacherClassService.addTeacherClass(teacherClass)
        return if (_teacherClass != null){
            ResponseFeedback(
                data = _teacherClass,
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

    override suspend fun updateTeacherClass(
        uniqueTeacherId: String,
        uniqueClassId: String,
        teacherClass: TeacherClass,
    ): ResponseFeedback<TeacherClass?> {
        val _updatedTeacherClass = teacherClassService.updateTeacherClass(uniqueTeacherId, uniqueClassId, teacherClass)
        return if (_updatedTeacherClass != null){
            ResponseFeedback(
                data = _updatedTeacherClass,
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

    override suspend fun deleteTeacherClass(teacherClass: TeacherClass): ResponseFeedback<String> {
        val deletedRows = teacherClassService.deleteTeacherClass(teacherClass)
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