package com.dedee.service.helper.student_parent

import com.dedee.entities.StudentParentEntity
import com.dedee.model.helper_tables.StudentParent
import com.dedee.model.parent.ParentInfo
import com.dedee.model.student.StudentInfo
import com.dedee.service.parent.ParentService
import com.dedee.service.student.StudentService
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.ktorm.database.Database
import org.ktorm.dsl.*
import util.Constants.emptyString
import util.DatabaseEntityFields.UniqueStudentId

class StudentParentServiceImpl(
    private val db: Database,
    private val studentService: StudentService,
    private val parentService: ParentService,
    private val gson: Gson,
) : StudentParentService {
    private val listOfStudentsInfoType = object : TypeToken<List<StudentInfo>>() {}.type
    private val listOfParentInfoType = object : TypeToken<List<ParentInfo>>() {}.type
    
    override suspend fun getAllStudentParents(): List<StudentParent>? {
        val result = db.from(StudentParentEntity)
            .select()
            .map {
                val uniqueStudentId = it[StudentParentEntity.uniqueStudentId] ?: emptyString
                val parentUsername = it[StudentParentEntity.parentUsername] ?: emptyString
                StudentParent(uniqueStudentId, parentUsername)
            }
        return result.ifEmpty { null }
    }

    override suspend fun getAllStudentsOfThisParent(parentUsername: String): List<StudentInfo>? {
        val uniqueStudentIds = db.from(StudentParentEntity)
            .select()
            .where { StudentParentEntity.parentUsername eq parentUsername }
            .map { it[StudentParentEntity.uniqueStudentId] }
        val students = mutableListOf<StudentInfo>()
        uniqueStudentIds.forEach { studentId->
            val student = studentId?.let { studentService.getStudents(it, UniqueStudentId)?.firstOrNull() }
            if (student != null){
                val firstName = student.firstName
                val lastName = student.lastName
                val otherNames = student.otherNames ?: emptyString
                val uniqueStudentId = student.uniqueStudentId
                val uniqueClassId = student.uniqueClassId ?: emptyString
                val gender = student.gender
                val photo = student.photo ?: emptyString
                val teachers = student.teachers ?: emptyString
                val gradings = student.gradings ?: emptyString
                val conducts = student.conducts ?: emptyString
                val isEnrolled = student.isEnrolled
                students.add(StudentInfo(firstName, lastName, otherNames, uniqueStudentId, uniqueClassId, gender, photo, teachers, gradings, conducts, isEnrolled))
            }
        }
        return students.ifEmpty { null }
    }

    override suspend fun getAllParentsOfThisStudent(uniqueStudentId: String): List<ParentInfo>? {
        val parentUsernames = db.from(StudentParentEntity)
            .select()
            .where { StudentParentEntity.uniqueStudentId eq uniqueStudentId }
            .map { it[StudentParentEntity.parentUsername] }
        val parents = mutableListOf<ParentInfo>()
        parentUsernames.forEach { _parentUsername->
            val parent = _parentUsername?.let { parentService.findParentByParentUserName(it) }
            if (parent != null){
                val firstName = parent.firstName
                val lastName = parent.lastName
                val parentUsername = parent.parentUsername
                val gender = parent.gender
                val email = parent.email
                val contact = parent.contact
                val hasAnEnrolledWard = parent.hasAnEnrolledWard
                parents.add(ParentInfo(firstName, lastName, parentUsername, email, contact, gender, hasAnEnrolledWard))
            }
        }
        return parents.ifEmpty { null }
    }

    override suspend fun addStudentParent(studentParent: StudentParent): StudentParent? {
        val uniqueStudentId = studentParent.uniqueStudentId
        val parentUsername = studentParent.parentUsername
        val result =  db.insert(StudentParentEntity){
            set(it.uniqueStudentId, studentParent.uniqueStudentId)
            set(it.parentUsername, studentParent.parentUsername)
        }
        return if (result < 1){
            null
        }else{
            val thisStudent = studentService.getStudents(uniqueStudentId, UniqueStudentId)?.firstOrNull()
            val thisParent = parentService.findParentByParentUserName(parentUsername)

            val parentsJSON = thisStudent?.parents
            val parentsInfoList: List<ParentInfo> = gson.fromJson(parentsJSON, listOfParentInfoType)
            val mutableListOfParentsInfo = parentsInfoList.toMutableList()
            thisParent?.toParentInfo()?.let { mutableListOfParentsInfo.add(it) }
            mutableListOfParentsInfo.removeIf { it.parentUsername ==  parentUsername }
            val updatedParentListJSON = gson.toJson(mutableListOfParentsInfo)
            studentService.addParents(updatedParentListJSON, uniqueStudentId)

            val studentsJSON = thisParent?.wards
            val studentsInfoList: List<StudentInfo> = gson.fromJson(studentsJSON, listOfStudentsInfoType)
            val mutableListOfStudentsInfo = studentsInfoList.toMutableList()
            thisStudent?.toStudentInfo()?.let { mutableListOfStudentsInfo.add(it) }
            mutableListOfStudentsInfo.removeIf { it.uniqueStudentId == uniqueStudentId }
            val updatedStudentListJSON = gson.toJson(mutableListOfParentsInfo)
            parentService.addWard(updatedStudentListJSON, uniqueStudentId)

            db.from(StudentParentEntity)
                .select()
                .where { StudentParentEntity.uniqueStudentId eq studentParent.uniqueStudentId }
                .where { StudentParentEntity.parentUsername eq studentParent.parentUsername }
                .map {
                    val thisUniqueStudentId = it[StudentParentEntity.uniqueStudentId] ?: emptyString
                    val thisParentUsername = it[StudentParentEntity.parentUsername] ?: emptyString
                    StudentParent(thisUniqueStudentId, thisParentUsername)
                }.firstOrNull()
        }
    }

    override suspend fun updateStudentParent(
        uniqueStudentId: String,
        parentUsername: String,
        studentParent: StudentParent,
    ): StudentParent? {
        val rowsAffected = db.update(StudentParentEntity){
            set(it.uniqueStudentId, studentParent.uniqueStudentId)
            set(it.parentUsername, studentParent.parentUsername)
            where { it.uniqueStudentId eq uniqueStudentId }
            where { it.parentUsername eq parentUsername }
        }
        return if (rowsAffected < 1){
            null
        }else {
            val updatedUniqueStudentId = studentParent.uniqueStudentId
            val updatedUniqueParentId = studentParent.parentUsername

            val oldParent = parentService.findParentByParentUserName(parentUsername)
            val oldStudent = studentService.getStudents(uniqueStudentId, UniqueStudentId)?.firstOrNull()

            val updatedParent = parentService.findParentByParentUserName(updatedUniqueParentId)
            val updatedStudent = studentService.getStudents(updatedUniqueStudentId, UniqueStudentId)?.firstOrNull()

            val studentsOfOldParentJSON = oldParent?.wards
            val listOf_StudentInfo_Of_OldParent: List<StudentInfo> = gson.fromJson(studentsOfOldParentJSON, listOfStudentsInfoType)
            val mutableList_Of_StudentInfo_Of_OldParent = listOf_StudentInfo_Of_OldParent.toMutableList()
            mutableList_Of_StudentInfo_Of_OldParent.removeIf { it.uniqueStudentId == uniqueStudentId }
            val updatedList_Of_Students_Of_OldParent_JSON = gson.toJson(mutableList_Of_StudentInfo_Of_OldParent)
            parentService.addWard(updatedList_Of_Students_Of_OldParent_JSON, parentUsername)

            val studentsOfUpdatedParentJSON = updatedParent?.wards
            val listOf_StudentInfo_Of_UpdatedParent: List<StudentInfo> = gson.fromJson(studentsOfUpdatedParentJSON, listOfStudentsInfoType)
            val mutableList_Of_StudentInfo_Of_UpdatedParent = listOf_StudentInfo_Of_UpdatedParent.toMutableList()
            mutableList_Of_StudentInfo_Of_UpdatedParent.removeIf { it.uniqueStudentId == updatedUniqueStudentId }
            updatedStudent?.toStudentInfo()?.let { mutableList_Of_StudentInfo_Of_UpdatedParent.add(it) }
            val updatedList_Of_Students_Of_UpdatedParent_JSON = gson.toJson(mutableList_Of_StudentInfo_Of_OldParent)
            parentService.addWard(updatedList_Of_Students_Of_UpdatedParent_JSON, parentUsername)

            val parentsOfOldStudentJSON = oldStudent?.parents
            val listOf_ParentInfo_Of_OldStudent: List<ParentInfo> = gson.fromJson(parentsOfOldStudentJSON, listOfParentInfoType)
            val mutableListOf_ParentInfo_Of_OldStudent = listOf_ParentInfo_Of_OldStudent.toMutableList()
            mutableListOf_ParentInfo_Of_OldStudent.removeIf { it.parentUsername == parentUsername }
            val updatedList_Of_Parents_Of_OldStudent_JSON = gson.toJson(mutableListOf_ParentInfo_Of_OldStudent)
            studentService.addParents(updatedList_Of_Parents_Of_OldStudent_JSON, uniqueStudentId)

            val parentsOfUpdatedStudentJSON = updatedStudent?.parents
            val listOf_ParentInfo_Of_UpdatedStudent: List<ParentInfo> = gson.fromJson(parentsOfUpdatedStudentJSON, listOfParentInfoType)
            val mutableListOf_ParentInfo_Of_UpdatedStudent = listOf_ParentInfo_Of_UpdatedStudent.toMutableList()
            mutableListOf_ParentInfo_Of_UpdatedStudent.removeIf { it.parentUsername == updatedUniqueParentId }
            updatedParent?.toParentInfo()?.let { mutableListOf_ParentInfo_Of_UpdatedStudent.add(it) }
            val updatedList_Of_Parents_Of_UpdatedStudent_JSON = gson.toJson(mutableListOf_ParentInfo_Of_UpdatedStudent)
            studentService.addParents(updatedList_Of_Parents_Of_UpdatedStudent_JSON, uniqueStudentId)


            db.from(StudentParentEntity)
                .select()
                .where { StudentParentEntity.uniqueStudentId eq studentParent.uniqueStudentId }
                .where { StudentParentEntity.parentUsername eq studentParent.parentUsername }
                .map {
                    val thisUniqueStudentId = it[StudentParentEntity.uniqueStudentId] ?: emptyString
                    val thisParentUsername = it[StudentParentEntity.parentUsername] ?: emptyString
                    StudentParent(thisUniqueStudentId, thisParentUsername)
                }.firstOrNull()
        }
    }

    override suspend fun deleteStudentParent(studentParent: StudentParent): Int {
        return db.delete(StudentParentEntity) {
            StudentParentEntity.uniqueStudentId eq studentParent.uniqueStudentId
            StudentParentEntity.parentUsername eq studentParent.parentUsername
        }
    }

}