package com.dedee.repository.helper.student_parent

import com.dedee.model.helper_tables.StudentParent
import com.dedee.model.parent.ParentInfo
import com.dedee.model.student.StudentInfo
import com.dedee.service.helper.student_parent.StudentParentService
import util.ResponseFeedback

class StudentParentRepositoryImpl(
    private val studentParentService: StudentParentService,
) : StudentParentRepository{
    override suspend fun getAllStudentParents(): ResponseFeedback<List<StudentParent>?> {
        val result = studentParentService.getAllStudentParents()
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

    override suspend fun getAllStudentsOfThisParent(parentUsername: String): ResponseFeedback<List<StudentInfo>?> {
        val studentInfo = studentParentService.getAllStudentsOfThisParent(parentUsername)
        return if (studentInfo != null){
            ResponseFeedback(
                data = studentInfo,
                message = "Students have been fetched successfully",
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

    override suspend fun getAllParentsOfThisStudent(uniqueStudentId: String): ResponseFeedback<List<ParentInfo>?> {
        val parentInfo = studentParentService.getAllParentsOfThisStudent(uniqueStudentId)
        return if (parentInfo != null){
            ResponseFeedback(
                data = parentInfo,
                message = "Parents have been fetched successfully",
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

    override suspend fun addStudentParent(studentParent: StudentParent): ResponseFeedback<StudentParent?> {
        val _studentParent = studentParentService.addStudentParent(studentParent)
        return if (_studentParent != null){
            ResponseFeedback(
                data = _studentParent,
                message = "Data have been fetched successfully",
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

    override suspend fun updateStudentParent(
        uniqueStudentId: String,
        parentUsername: String,
        studentParent: StudentParent,
    ): ResponseFeedback<StudentParent?> {
        val _updatedStudentParent = studentParentService.updateStudentParent(uniqueStudentId, parentUsername, studentParent)
        return if (_updatedStudentParent != null){
            ResponseFeedback(
                data = _updatedStudentParent,
                message = "Students have been fetched successfully",
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

    override suspend fun deleteStudentParent(studentParent: StudentParent): ResponseFeedback<String> {
        val deletedRows = studentParentService.deleteStudentParent(studentParent)
        return if (deletedRows != null && deletedRows  > 0){
            ResponseFeedback(
                data = "Information deleted successfully",
                message = "Information deleted successfully",
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