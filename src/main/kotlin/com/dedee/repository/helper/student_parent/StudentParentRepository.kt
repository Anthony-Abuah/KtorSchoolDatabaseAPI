package com.dedee.repository.helper.student_parent

import com.dedee.model.helper_tables.StudentParent
import com.dedee.model.parent.ParentInfo
import com.dedee.model.student.StudentInfo
import com.dedee.model.subject.SubjectRequest
import com.dedee.model.subject.SubjectResponse
import util.ResponseFeedback


interface StudentParentRepository {
    suspend fun getAllStudentParents(): ResponseFeedback<List<StudentParent>?>

    suspend fun getAllStudentsOfThisParent(parentUsername: String): ResponseFeedback<List<StudentInfo>?>

    suspend fun getAllParentsOfThisStudent(uniqueStudentId: String): ResponseFeedback<List<ParentInfo>?>

    suspend fun addStudentParent(studentParent: StudentParent): ResponseFeedback<StudentParent?>

    suspend fun updateStudentParent(uniqueStudentId: String, parentUsername: String, studentParent: StudentParent): ResponseFeedback<StudentParent?>

    suspend fun deleteStudentParent(studentParent: StudentParent): ResponseFeedback<String>

    
}



