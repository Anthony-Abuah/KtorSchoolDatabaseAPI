package com.dedee.service.helper.teacher_subject

import com.dedee.entities.TeacherSubjectEntity
import com.dedee.model.helper_tables.TeacherSubject
import com.dedee.model.subject.SubjectInfo
import com.dedee.model.teacher.TeacherInfo
import com.dedee.service.subject.SubjectService
import com.dedee.service.teacher.TeacherService
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.ktorm.database.Database
import org.ktorm.dsl.*
import util.Constants.emptyString
import util.DatabaseEntityFields.UniqueSubjectId

class TeacherSubjectServiceImpl(
    private val db: Database,
    private val teacherService: TeacherService,
    private val subjectService: SubjectService,
    private val gson: Gson
) : TeacherSubjectService {
    private val listOfSubjectInfoType = object : TypeToken<List<SubjectInfo>>() {}.type
    private val listOfTeachersInfoType = object : TypeToken<List<TeacherInfo>>() {}.type


    override suspend fun getAllTeacherSubjects(): List<TeacherSubject>? {
        val result = db.from(TeacherSubjectEntity)
            .select()
            .map {
                val uniqueTeacherId = it[TeacherSubjectEntity.uniqueTeacherId] ?: emptyString
                val uniqueSubjectId = it[TeacherSubjectEntity.uniqueSubjectId] ?: emptyString
                TeacherSubject(uniqueTeacherId, uniqueSubjectId)
            }
        return result.ifEmpty { null }
    }

    override suspend fun getAllTeachersOfThisSubject(uniqueSubjectId: String): List<TeacherInfo>? {
        val uniqueTeacherIds = db.from(TeacherSubjectEntity)
            .select()
            .where { TeacherSubjectEntity.uniqueSubjectId eq uniqueSubjectId }
            .map { it[TeacherSubjectEntity.uniqueTeacherId] }
        val teachers = mutableListOf<TeacherInfo>()
        uniqueTeacherIds.forEach { teacherId->
            val teacher = teacherId?.let { teacherService.findTeacherByUniqueTeacherId(it) }
            if (teacher != null){
                val firstName = teacher.firstName
                val lastName = teacher.lastName
                val otherNames = teacher.otherNames ?: emptyString
                val teacherUsername = teacher.teacherUsername
                val uniqueTeacherId = teacher.uniqueTeacherId
                val email = teacher.email
                val contact = teacher.contact
                val gender = teacher.gender
                val isStillAtThisSchool = teacher.isStillAtThisSchool
                teachers.add(TeacherInfo(firstName, lastName, otherNames, teacherUsername, uniqueTeacherId, email, contact, gender, isStillAtThisSchool))
            }
        }
        return teachers.ifEmpty { null }
    }

    override suspend fun getAllSubjectsOfThisTeacher(uniqueTeacherId: String): List<SubjectInfo>? {
        val uniqueSubjectIds = db.from(TeacherSubjectEntity)
            .select()
            .where { TeacherSubjectEntity.uniqueTeacherId eq uniqueTeacherId }
            .map { it[TeacherSubjectEntity.uniqueTeacherId] }
        val subjects = mutableListOf<SubjectInfo>()
        uniqueSubjectIds.forEach { subjectId->
            val subject = subjectId?.let { subjectService.getSubject(it, UniqueSubjectId)?.firstOrNull() }
            if (subject != null){
                val subjectName = subject.subjectName ?: emptyString
                val uniqueSubjectId = subject.uniqueSubjectId ?: emptyString
                val teachers = subject.teachers ?: emptyString
                subjects.add(SubjectInfo(subjectName, uniqueSubjectId, teachers))
            }
        }
        return subjects.ifEmpty { null }
    }


    override suspend fun addTeacherSubject(teacherSubject: TeacherSubject): TeacherSubject? {
        val uniqueTeacherId = teacherSubject.uniqueTeacherId
        val uniqueSubjectId = teacherSubject.uniqueSubjectId
        val result =  db.insert(TeacherSubjectEntity){
            set(it.uniqueTeacherId, teacherSubject.uniqueTeacherId)
            set(it.uniqueSubjectId, teacherSubject.uniqueSubjectId)
        }
        return if (result < 1){
            null
        }else{
            val thisTeacher = teacherService.findTeacherByUniqueTeacherId(uniqueTeacherId)
            val thisSubject = subjectService.getSubject(uniqueSubjectId, UniqueSubjectId)?.firstOrNull()

            val subjectsJSON = thisTeacher?.subjects
            val subjectsInfoList: List<SubjectInfo> = gson.fromJson(subjectsJSON, listOfSubjectInfoType)
            val mutableListOfSubjectsInfo = subjectsInfoList.toMutableList()
            thisSubject?.toSubjectInfo()?.let { mutableListOfSubjectsInfo.add(it) }
            val updatedSubjectListJSON = gson.toJson(mutableListOfSubjectsInfo)
            teacherService.addSubjects(updatedSubjectListJSON, uniqueTeacherId)

            val teachersJSON = thisSubject?.teachers
            val teachersInfoList: List<TeacherInfo> = gson.fromJson(teachersJSON, listOfTeachersInfoType)
            val mutableListOfTeachersInfo = teachersInfoList.toMutableList()
            thisTeacher?.toTeacherInfo()?.let { mutableListOfTeachersInfo.add(it) }
            val updatedTeacherListJSON = gson.toJson(mutableListOfSubjectsInfo)
            subjectService.addTeachers(updatedTeacherListJSON, uniqueTeacherId)

            db.from(TeacherSubjectEntity)
                .select()
                .where { TeacherSubjectEntity.uniqueTeacherId eq teacherSubject.uniqueTeacherId }
                .where { TeacherSubjectEntity.uniqueSubjectId eq teacherSubject.uniqueSubjectId }
                .map {
                    val thisUniqueTeacherId = it[TeacherSubjectEntity.uniqueTeacherId] ?: emptyString
                    val thisUniqueSubjectId = it[TeacherSubjectEntity.uniqueSubjectId] ?: emptyString
                    TeacherSubject(thisUniqueTeacherId, thisUniqueSubjectId)
                }.firstOrNull()
        }
    }

    override suspend fun updateTeacherSubject(uniqueTeacherId: String, uniqueSubjectId: String, teacherSubject: TeacherSubject): TeacherSubject? {
        val rowsAffected = db.update(TeacherSubjectEntity){
            set(it.uniqueTeacherId, teacherSubject.uniqueTeacherId)
            set(it.uniqueSubjectId, teacherSubject.uniqueSubjectId)
            where { it.uniqueTeacherId eq uniqueTeacherId }
            where { it.uniqueSubjectId eq uniqueSubjectId }
        }
        return if (rowsAffected < 1){
            null
        }else {

            val updatedUniqueSubjectId = teacherSubject.uniqueSubjectId
            val updatedUniqueTeacherId = teacherSubject.uniqueTeacherId

            val oldTeacher = teacherService.findTeacherByUniqueTeacherId(uniqueTeacherId)
            val oldSubject = subjectService.getSubject(uniqueSubjectId, UniqueSubjectId)?.firstOrNull()

            val updatedTeacher = teacherService.findTeacherByUniqueTeacherId(updatedUniqueTeacherId)
            val updatedSubject = subjectService.getSubject(updatedUniqueSubjectId, UniqueSubjectId)?.firstOrNull()

            val subjectsOfOldTeacherJSON = oldTeacher?.subjects
            val listOf_SubjectInfo_Of_OldTeacher: List<SubjectInfo> = gson.fromJson(subjectsOfOldTeacherJSON, listOfSubjectInfoType)
            val mutableList_Of_SubjectInfo_Of_OldTeacher = listOf_SubjectInfo_Of_OldTeacher.toMutableList()
            mutableList_Of_SubjectInfo_Of_OldTeacher.removeIf { it.uniqueSubjectId == uniqueSubjectId }
            val updatedList_Of_Subjects_Of_OldTeacher_JSON = gson.toJson(mutableList_Of_SubjectInfo_Of_OldTeacher)
            teacherService.addSubjects(updatedList_Of_Subjects_Of_OldTeacher_JSON, uniqueTeacherId)

            val subjectsOfUpdatedTeacherJSON = updatedTeacher?.subjects
            val listOf_SubjectInfo_Of_UpdatedTeacher: List<SubjectInfo> = gson.fromJson(subjectsOfUpdatedTeacherJSON, listOfSubjectInfoType)
            val mutableList_Of_SubjectInfo_Of_UpdatedTeacher = listOf_SubjectInfo_Of_UpdatedTeacher.toMutableList()
            mutableList_Of_SubjectInfo_Of_UpdatedTeacher.removeIf { it.uniqueSubjectId == updatedUniqueSubjectId }
            updatedSubject?.toSubjectInfo()?.let { mutableList_Of_SubjectInfo_Of_UpdatedTeacher.add(it) }
            val updatedList_Of_Subjects_Of_UpdatedTeacher_JSON = gson.toJson(mutableList_Of_SubjectInfo_Of_OldTeacher)
            teacherService.addSubjects(updatedList_Of_Subjects_Of_UpdatedTeacher_JSON, uniqueTeacherId)

            val teachersOfOldSubjectJSON = oldSubject?.teachers
            val listOf_TeacherInfo_Of_OldSubject: List<TeacherInfo> = gson.fromJson(teachersOfOldSubjectJSON, listOfTeachersInfoType)
            val mutableListOf_TeacherInfo_Of_OldSubject = listOf_TeacherInfo_Of_OldSubject.toMutableList()
            mutableListOf_TeacherInfo_Of_OldSubject.removeIf { it.uniqueTeacherId == uniqueTeacherId }
            val updatedList_Of_Teachers_Of_OldSubject_JSON = gson.toJson(mutableListOf_TeacherInfo_Of_OldSubject)
            subjectService.addTeachers(updatedList_Of_Teachers_Of_OldSubject_JSON, uniqueSubjectId)

            val teachersOfUpdatedSubjectJSON = updatedSubject?.teachers
            val listOf_TeacherInfo_Of_UpdatedSubject: List<TeacherInfo> = gson.fromJson(teachersOfUpdatedSubjectJSON, listOfTeachersInfoType)
            val mutableListOf_TeacherInfo_Of_UpdatedSubject = listOf_TeacherInfo_Of_UpdatedSubject.toMutableList()
            mutableListOf_TeacherInfo_Of_UpdatedSubject.removeIf { it.uniqueTeacherId == updatedUniqueTeacherId }
            updatedTeacher?.toTeacherInfo()?.let { mutableListOf_TeacherInfo_Of_UpdatedSubject.add(it) }
            val updatedList_Of_Teachers_Of_UpdatedSubject_JSON = gson.toJson(mutableListOf_TeacherInfo_Of_UpdatedSubject)
            subjectService.addTeachers(updatedList_Of_Teachers_Of_UpdatedSubject_JSON, uniqueSubjectId)


            db.from(TeacherSubjectEntity)
                .select()
                .where { TeacherSubjectEntity.uniqueTeacherId eq teacherSubject.uniqueTeacherId }
                .where { TeacherSubjectEntity.uniqueSubjectId eq teacherSubject.uniqueSubjectId }
                .map {
                    val thisUniqueTeacherId = it[TeacherSubjectEntity.uniqueTeacherId] ?: emptyString
                    val thisUniqueSubjectId = it[TeacherSubjectEntity.uniqueSubjectId] ?: emptyString
                    TeacherSubject(thisUniqueTeacherId, thisUniqueSubjectId)
                }.firstOrNull()
        }
    }

    override suspend fun deleteTeacherSubject(teacherSubject: TeacherSubject): Int {
        return db.delete(TeacherSubjectEntity) {
            TeacherSubjectEntity.uniqueSubjectId eq teacherSubject.uniqueSubjectId
            TeacherSubjectEntity.uniqueTeacherId eq teacherSubject.uniqueTeacherId
        }
    }

}