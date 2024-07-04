package com.dedee.service.helper.teacher_class

import com.dedee.entities.TeacherClassEntity
import com.dedee.model.helper_tables.TeacherClass
import com.dedee.model.school_class.SchoolClassInfo
import com.dedee.model.teacher.TeacherInfo
import com.dedee.service.school_class.SchoolClassService
import com.dedee.service.teacher.TeacherService
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.ktorm.database.Database
import org.ktorm.dsl.*
import util.Constants.emptyString
import util.DatabaseEntityFields.UniqueClassId

class TeacherClassServiceImpl(
    private val db: Database,
    private val teacherService: TeacherService,
    private val schoolClassService: SchoolClassService,
    private val gson: Gson
) : TeacherClassService {
    private val listOfClassInfoType = object : TypeToken<List<SchoolClassInfo>>() {}.type
    private val listOfTeachersInfoType = object : TypeToken<List<TeacherInfo>>() {}.type

    override suspend fun getAllTeacherClasses(): List<TeacherClass>? {
        val results = db.from(TeacherClassEntity)
            .select()
            .map {
                val uniqueTeacherId = it[TeacherClassEntity.uniqueTeacherId] ?: emptyString
                val uniqueClassId = it[TeacherClassEntity.uniqueClassId] ?: emptyString
                TeacherClass(uniqueTeacherId, uniqueClassId)
            }
        return results.ifEmpty { null }
    }

    override suspend fun getAllTeachersOfThisClass(uniqueClassId: String): List<TeacherInfo>? {
        val results = db.from(TeacherClassEntity)
            .select()
            .where { TeacherClassEntity.uniqueClassId eq uniqueClassId }
            .map { it[TeacherClassEntity.uniqueTeacherId] }
        val teachers = mutableListOf<TeacherInfo>()
        results.forEach { result->
            val teacher = result?.let { teacherService.findTeacherByUniqueTeacherId(it) }
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

    override suspend fun getAllClassesOfThisTeacher(uniqueTeacherId: String): List<SchoolClassInfo>? {
        val uniqueTeacherIds = db.from(TeacherClassEntity)
            .select()
            .where { TeacherClassEntity.uniqueTeacherId eq uniqueTeacherId }
            .map { it[TeacherClassEntity.uniqueTeacherId] }
        val classes = mutableListOf<SchoolClassInfo>()
        uniqueTeacherIds.forEach { teacherId->
            val schoolClass = teacherId?.let { schoolClassService.getSchoolClass(it, UniqueClassId)?.firstOrNull() }
            if (schoolClass != null){
                val className = schoolClass.className
                val uniqueClassId = schoolClass.uniqueClassId
                val classStage = schoolClass.classStage
                val academicYear = schoolClass.academicYear
                val numberOfBoys = schoolClass.numberOfBoys
                val numberOfGirls = schoolClass.numberOfGirls
                val totalNumberOfStudents = schoolClass.totalNumberOfStudents
                val teachers = schoolClass.teachers
                val subjects = schoolClass.subjects
                val students = schoolClass.students
                classes.add(SchoolClassInfo(className, uniqueClassId, classStage, academicYear, numberOfBoys, numberOfGirls, totalNumberOfStudents, teachers, subjects, students))
            }
        }
        return classes.ifEmpty { null }
    }

    override suspend fun addTeacherClass(teacherClass: TeacherClass): TeacherClass? {
        val uniqueTeacherId = teacherClass.uniqueTeacherId
        val uniqueClassId = teacherClass.uniqueClassId
        val result =  db.insert(TeacherClassEntity){
            set(it.uniqueTeacherId, teacherClass.uniqueTeacherId)
            set(it.uniqueClassId, teacherClass.uniqueClassId)
        }
        return if (result < 1){
            null
        }else{
            val thisTeacher = teacherService.findTeacherByUniqueTeacherId(uniqueTeacherId)
            val thisClass = schoolClassService.getSchoolClass(uniqueClassId, UniqueClassId)?.firstOrNull()

            val classesJSON = thisTeacher?.classes
            val classesInfoList: List<SchoolClassInfo> = gson.fromJson(classesJSON, listOfClassInfoType)
            val mutableListOfClassesInfo = classesInfoList.toMutableList()
            thisClass?.toSchoolClassInfo()?.let { mutableListOfClassesInfo.add(it) }
            val updatedClassListJSON = gson.toJson(mutableListOfClassesInfo)
            teacherService.addClasses(updatedClassListJSON, uniqueTeacherId)

            val teachersJSON = thisClass?.teachers
            val teachersInfoList: List<TeacherInfo> = gson.fromJson(teachersJSON, listOfTeachersInfoType)
            val mutableListOfTeachersInfo = teachersInfoList.toMutableList()
            thisTeacher?.toTeacherInfo()?.let { mutableListOfTeachersInfo.add(it) }
            val updatedTeacherListJSON = gson.toJson(mutableListOfClassesInfo)
            schoolClassService.addTeachers(updatedTeacherListJSON, uniqueTeacherId)

            db.from(TeacherClassEntity)
                .select()
                .where { TeacherClassEntity.uniqueTeacherId eq teacherClass.uniqueTeacherId }
                .where { TeacherClassEntity.uniqueClassId eq teacherClass.uniqueClassId }
                .map {
                    val thisUniqueTeacherId = it[TeacherClassEntity.uniqueTeacherId] ?: emptyString
                    val thisUniqueClassId = it[TeacherClassEntity.uniqueClassId] ?: emptyString
                    TeacherClass(thisUniqueTeacherId, thisUniqueClassId)
                }.firstOrNull()
        }
    }

    override suspend fun updateTeacherClass(
        uniqueTeacherId: String,
        uniqueClassId: String,
        teacherClass: TeacherClass,
    ): TeacherClass? {
        val rowsAffected = db.update(TeacherClassEntity){
            set(it.uniqueTeacherId, teacherClass.uniqueTeacherId)
            set(it.uniqueClassId, teacherClass.uniqueClassId)
            where { it.uniqueTeacherId eq uniqueTeacherId }
            where { it.uniqueClassId eq uniqueClassId }
        }
        return if (rowsAffected < 1){
            null
        }else {
            val updatedUniqueClassId = teacherClass.uniqueClassId
            val updatedUniqueTeacherId = teacherClass.uniqueTeacherId

            val oldTeacher = teacherService.findTeacherByUniqueTeacherId(uniqueTeacherId)
            val oldClass = schoolClassService.getSchoolClass(uniqueClassId, UniqueClassId)?.firstOrNull()

            val updatedTeacher = teacherService.findTeacherByUniqueTeacherId(updatedUniqueTeacherId)
            val updatedClass = schoolClassService.getSchoolClass(updatedUniqueClassId, UniqueClassId)?.firstOrNull()

            val classesOfOldTeacherJSON = oldTeacher?.classes
            val listOf_ClassInfo_Of_OldTeacher: List<SchoolClassInfo> = gson.fromJson(classesOfOldTeacherJSON, listOfClassInfoType)
            val mutableList_Of_ClassInfo_Of_OldTeacher = listOf_ClassInfo_Of_OldTeacher.toMutableList()
            mutableList_Of_ClassInfo_Of_OldTeacher.removeIf { it.uniqueClassId == uniqueClassId }
            val updatedList_Of_Classes_Of_OldTeacher_JSON = gson.toJson(mutableList_Of_ClassInfo_Of_OldTeacher)
            teacherService.addClasses(updatedList_Of_Classes_Of_OldTeacher_JSON, uniqueTeacherId)

            val classesOfUpdatedTeacherJSON = updatedTeacher?.classes
            val listOf_ClassInfo_Of_UpdatedTeacher: List<SchoolClassInfo> = gson.fromJson(classesOfUpdatedTeacherJSON, listOfClassInfoType)
            val mutableList_Of_ClassInfo_Of_UpdatedTeacher = listOf_ClassInfo_Of_UpdatedTeacher.toMutableList()
            mutableList_Of_ClassInfo_Of_UpdatedTeacher.removeIf { it.uniqueClassId == updatedUniqueClassId }
            updatedClass?.toSchoolClassInfo()?.let { mutableList_Of_ClassInfo_Of_UpdatedTeacher.add(it) }
            val updatedList_Of_Classes_Of_UpdatedTeacher_JSON = gson.toJson(mutableList_Of_ClassInfo_Of_OldTeacher)
            teacherService.addClasses(updatedList_Of_Classes_Of_UpdatedTeacher_JSON, uniqueTeacherId)

            val teachersOfOldClassJSON = oldClass?.teachers
            val listOf_TeacherInfo_Of_OldClass: List<TeacherInfo> = gson.fromJson(teachersOfOldClassJSON, listOfTeachersInfoType)
            val mutableListOf_TeacherInfo_Of_OldClass = listOf_TeacherInfo_Of_OldClass.toMutableList()
            mutableListOf_TeacherInfo_Of_OldClass.removeIf { it.uniqueTeacherId == uniqueTeacherId }
            val updatedList_Of_Teachers_Of_OldClass_JSON = gson.toJson(mutableListOf_TeacherInfo_Of_OldClass)
            schoolClassService.addTeachers(updatedList_Of_Teachers_Of_OldClass_JSON, uniqueClassId)

            val teachersOfUpdatedClassJSON = updatedClass?.teachers
            val listOf_TeacherInfo_Of_UpdatedClass: List<TeacherInfo> = gson.fromJson(teachersOfUpdatedClassJSON, listOfTeachersInfoType)
            val mutableListOf_TeacherInfo_Of_UpdatedClass = listOf_TeacherInfo_Of_UpdatedClass.toMutableList()
            mutableListOf_TeacherInfo_Of_UpdatedClass.removeIf { it.uniqueTeacherId == updatedUniqueTeacherId }
            updatedTeacher?.toTeacherInfo()?.let { mutableListOf_TeacherInfo_Of_UpdatedClass.add(it) }
            val updatedList_Of_Teachers_Of_UpdatedClass_JSON = gson.toJson(mutableListOf_TeacherInfo_Of_UpdatedClass)
            schoolClassService.addTeachers(updatedList_Of_Teachers_Of_UpdatedClass_JSON, uniqueClassId)

            db.from(TeacherClassEntity)
                .select()
                .where { TeacherClassEntity.uniqueTeacherId eq teacherClass.uniqueTeacherId }
                .where { TeacherClassEntity.uniqueClassId eq teacherClass.uniqueClassId }
                .map {
                    val thisUniqueTeacherId = it[TeacherClassEntity.uniqueTeacherId] ?: emptyString
                    val thisUniqueClassId = it[TeacherClassEntity.uniqueClassId] ?: emptyString
                    TeacherClass(thisUniqueTeacherId, thisUniqueClassId)
                }.firstOrNull()
        }
    }

    override suspend fun deleteTeacherClass(teacherClass: TeacherClass): Int {
        return db.delete(TeacherClassEntity) {
            TeacherClassEntity.uniqueClassId eq teacherClass.uniqueClassId
            TeacherClassEntity.uniqueTeacherId eq teacherClass.uniqueTeacherId
        }
    }


}