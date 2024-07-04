package com.dedee.service.helper.subject_class

import com.dedee.entities.SubjectClassEntity
import com.dedee.model.helper_tables.SubjectClass
import com.dedee.model.school_class.SchoolClassInfo
import com.dedee.model.subject.SubjectInfo
import com.dedee.service.school_class.SchoolClassService
import com.dedee.service.subject.SubjectService
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.ktorm.database.Database
import org.ktorm.dsl.*
import util.Constants.emptyString
import util.DatabaseEntityFields.UniqueClassId
import util.DatabaseEntityFields.UniqueSubjectId

class SubjectClassServiceImpl(
    private val db: Database,
    private val subjectService: SubjectService,
    private val schoolClassService: SchoolClassService,
    private val gson: Gson,
) : SubjectClassService {
    private val listOfClassesInfoType = object : TypeToken<List<SchoolClassInfo>>() {}.type
    private val listOfSubjectsInfoType = object : TypeToken<List<SubjectInfo>>() {}.type

    override suspend fun getAllSubjectClasses(): List<SubjectClass>? {
        val results = db.from(SubjectClassEntity)
            .select()
            .map {
                val uniqueSubjectId = it[SubjectClassEntity.uniqueSubjectId] ?: emptyString
                val uniqueClassId = it[SubjectClassEntity.uniqueClassId] ?: emptyString
                SubjectClass(uniqueSubjectId, uniqueClassId)
            }
        return results.ifEmpty { null }
    }

    override suspend fun getAllSubjectsOfThisClass(uniqueClassId: String): List<SubjectInfo>? {
        val results = db.from(SubjectClassEntity)
            .select()
            .where { SubjectClassEntity.uniqueClassId eq uniqueClassId }
            .map { it[SubjectClassEntity.uniqueSubjectId] }
        val subjects = mutableListOf<SubjectInfo>()
        results.forEach { result->
            val subject = result?.let { subjectService.getSubject(it, UniqueSubjectId)?.firstOrNull() }
            if (subject != null){
                val subjectName = subject.subjectName
                val uniqueSubjectId = subject.uniqueSubjectId
                val teachers = subject.teachers
                subjects.add(SubjectInfo(subjectName, uniqueSubjectId, teachers))
            }
        }
        return subjects.ifEmpty { null }
    }

    override suspend fun getAllClassesOfThisSubject(uniqueSubjectId: String): List<SchoolClassInfo>? {
        val uniqueSubjectIds = db.from(SubjectClassEntity)
            .select()
            .where { SubjectClassEntity.uniqueSubjectId eq uniqueSubjectId }
            .map { it[SubjectClassEntity.uniqueSubjectId] }
        val classes = mutableListOf<SchoolClassInfo>()
        uniqueSubjectIds.forEach { subjectId->
            val schoolClass = subjectId?.let { schoolClassService.getSchoolClass(it, UniqueClassId)?.firstOrNull() }
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

    override suspend fun addSubjectClass(subjectClass: SubjectClass): SubjectClass? {
        val uniqueSubjectId = subjectClass.uniqueSubjectId
        val uniqueClassId = subjectClass.uniqueClassId
        val result =  db.insert(SubjectClassEntity){
            set(it.uniqueSubjectId, uniqueSubjectId)
            set(it.uniqueClassId, uniqueClassId)
        }
        return if (result < 1){
            null
        }else{
            val thisSubject = subjectService.getSubject(uniqueSubjectId, UniqueSubjectId)?.firstOrNull()
            val thisClass = schoolClassService.getSchoolClass(uniqueClassId, UniqueClassId)?.firstOrNull()



            val classesJSON = thisSubject?.classes
            val classesInfoList: List<SchoolClassInfo> = gson.fromJson(classesJSON, listOfClassesInfoType)
            val mutableListOfClassesInfo = classesInfoList.toMutableList()
            thisClass?.toSchoolClassInfo()?.let { mutableListOfClassesInfo.add(it) }
            val updatedClassListJSON = gson.toJson(mutableListOfClassesInfo)
            subjectService.addClasses(updatedClassListJSON, uniqueSubjectId)

            val subjectsJSON = thisClass?.subjects
            val subjectsInfoList: List<SubjectInfo> = gson.fromJson(subjectsJSON, listOfSubjectsInfoType)
            val mutableListOfSubjectsInfo = subjectsInfoList.toMutableList()
            thisSubject?.toSubjectInfo()?.let { mutableListOfSubjectsInfo.add(it) }
            val updatedSubjectListJSON = gson.toJson(mutableListOfSubjectsInfo)
            schoolClassService.addSubjects(updatedSubjectListJSON, uniqueClassId)

            db.from(SubjectClassEntity)
                .select()
                .where { SubjectClassEntity.uniqueSubjectId eq subjectClass.uniqueSubjectId }
                .where { SubjectClassEntity.uniqueClassId eq subjectClass.uniqueClassId }
                .map {
                    val thisUniqueSubjectId = it[SubjectClassEntity.uniqueSubjectId] ?: emptyString
                    val thisUniqueClassId = it[SubjectClassEntity.uniqueClassId] ?: emptyString
                    SubjectClass(thisUniqueSubjectId, thisUniqueClassId)
                }.firstOrNull()
        }
    }

    override suspend fun updateSubjectClass(
        uniqueSubjectId: String,
        uniqueClassId: String,
        subjectClass: SubjectClass,
    ): SubjectClass? {
        val updatedUniqueSubjectId = subjectClass.uniqueSubjectId
        val updatedUniqueClassId = subjectClass.uniqueClassId
        val rowsAffected = db.update(SubjectClassEntity){
            set(it.uniqueSubjectId, updatedUniqueSubjectId)
            set(it.uniqueClassId, updatedUniqueClassId)
            where { it.uniqueSubjectId eq uniqueSubjectId }
            where { it.uniqueClassId eq uniqueClassId }
        }
        return if (rowsAffected < 1){
            null
        }else {

            val oldSubject = subjectService.getSubject(uniqueSubjectId, UniqueSubjectId)?.firstOrNull()
            val oldClass = schoolClassService.getSchoolClass(uniqueClassId, UniqueClassId)?.firstOrNull()

            val updatedSubject = subjectService.getSubject(updatedUniqueSubjectId, UniqueSubjectId)?.firstOrNull()
            val updatedClass = schoolClassService.getSchoolClass(updatedUniqueClassId, UniqueClassId)?.firstOrNull()

            val classesOfOldSubjectJSON = oldSubject?.classes
            val listOf_ClassInfo_Of_OldSubject: List<SchoolClassInfo> = gson.fromJson(classesOfOldSubjectJSON, listOfClassesInfoType)
            val mutableList_Of_ClassInfo_Of_OldSubject = listOf_ClassInfo_Of_OldSubject.toMutableList()
            mutableList_Of_ClassInfo_Of_OldSubject.removeIf { it.uniqueClassId == uniqueClassId }
            val updatedList_Of_Classes_Of_OldSubject_JSON = gson.toJson(mutableList_Of_ClassInfo_Of_OldSubject)
            subjectService.addClasses(updatedList_Of_Classes_Of_OldSubject_JSON, uniqueSubjectId)

            val classesOfUpdatedSubjectJSON = updatedSubject?.classes
            val listOf_ClassInfo_Of_UpdatedSubject: List<SchoolClassInfo> = gson.fromJson(classesOfUpdatedSubjectJSON, listOfClassesInfoType)
            val mutableList_Of_ClassInfo_Of_UpdatedSubject = listOf_ClassInfo_Of_UpdatedSubject.toMutableList()
            mutableList_Of_ClassInfo_Of_UpdatedSubject.removeIf { it.uniqueClassId == updatedUniqueClassId }
            updatedClass?.toSchoolClassInfo()?.let { mutableList_Of_ClassInfo_Of_UpdatedSubject.add(it) }
            val updatedList_Of_Classes_Of_UpdatedSubject_JSON = gson.toJson(mutableList_Of_ClassInfo_Of_OldSubject)
            subjectService.addClasses(updatedList_Of_Classes_Of_UpdatedSubject_JSON, uniqueSubjectId)

            val subjectsOfOldClassJSON = oldClass?.subjects
            val listOf_SubjectInfo_Of_OldClass: List<SubjectInfo> = gson.fromJson(subjectsOfOldClassJSON, listOfSubjectsInfoType)
            val mutableListOf_SubjectInfo_Of_OldClass = listOf_SubjectInfo_Of_OldClass.toMutableList()
            mutableListOf_SubjectInfo_Of_OldClass.removeIf { it.uniqueSubjectId == uniqueSubjectId }
            val updatedList_Of_Subjects_Of_OldClass_JSON = gson.toJson(mutableListOf_SubjectInfo_Of_OldClass)
            schoolClassService.addSubjects(updatedList_Of_Subjects_Of_OldClass_JSON, uniqueClassId)

            val subjectsOfUpdatedClassJSON = updatedClass?.subjects
            val listOf_SubjectInfo_Of_UpdatedClass: List<SubjectInfo> = gson.fromJson(subjectsOfUpdatedClassJSON, listOfSubjectsInfoType)
            val mutableListOf_SubjectInfo_Of_UpdatedClass = listOf_SubjectInfo_Of_UpdatedClass.toMutableList()
            mutableListOf_SubjectInfo_Of_UpdatedClass.removeIf { it.uniqueSubjectId == updatedUniqueSubjectId }
            updatedSubject?.toSubjectInfo()?.let { mutableListOf_SubjectInfo_Of_UpdatedClass.add(it) }
            val updatedList_Of_Subjects_Of_UpdatedClass_JSON = gson.toJson(mutableListOf_SubjectInfo_Of_UpdatedClass)
            schoolClassService.addSubjects(updatedList_Of_Subjects_Of_UpdatedClass_JSON, uniqueClassId)

            db.from(SubjectClassEntity)
                .select()
                .where { SubjectClassEntity.uniqueSubjectId eq subjectClass.uniqueSubjectId }
                .where { SubjectClassEntity.uniqueClassId eq subjectClass.uniqueClassId }
                .map {
                    val thisUniqueSubjectId = it[SubjectClassEntity.uniqueSubjectId] ?: emptyString
                    val thisUniqueClassId = it[SubjectClassEntity.uniqueClassId] ?: emptyString
                    SubjectClass(thisUniqueSubjectId, thisUniqueClassId)
                }.firstOrNull()
        }
    }

    override suspend fun deleteSubjectClass(subjectClass: SubjectClass): Int {
        return db.delete(SubjectClassEntity) {
            SubjectClassEntity.uniqueClassId eq subjectClass.uniqueClassId
            SubjectClassEntity.uniqueSubjectId eq subjectClass.uniqueSubjectId
        }
    }
}