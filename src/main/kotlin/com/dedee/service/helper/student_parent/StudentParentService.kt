package com.dedee.service.helper.student_parent

import com.dedee.model.helper_tables.StudentParent
import com.dedee.model.parent.ParentInfo
import com.dedee.model.student.StudentInfo

interface StudentParentService {

    suspend fun getAllStudentParents(): List<StudentParent>?

    suspend fun getAllStudentsOfThisParent(parentUsername: String): List<StudentInfo>?

    suspend fun getAllParentsOfThisStudent(uniqueStudentId: String): List<ParentInfo>?

    suspend fun addStudentParent(studentParent: StudentParent): StudentParent?

    suspend fun updateStudentParent(uniqueStudentId: String, parentUsername: String, studentParent: StudentParent): StudentParent?

    suspend fun deleteStudentParent(studentParent: StudentParent): Int?

}