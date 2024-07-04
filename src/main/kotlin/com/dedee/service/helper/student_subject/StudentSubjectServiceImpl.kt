package com.dedee.service.helper.student_subject

import com.dedee.entities.StudentSubjectEntity
import com.dedee.model.helper_tables.StudentSubject
import com.dedee.model.student.StudentInfo
import com.dedee.model.subject.SubjectInfo
import com.dedee.service.student.StudentService
import com.dedee.service.subject.SubjectService
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.ktorm.database.Database
import org.ktorm.dsl.*
import util.Constants.emptyString
import util.DatabaseEntityFields.UniqueStudentId
import util.DatabaseEntityFields.UniqueSubjectId

class StudentSubjectServiceImpl(
    private val db: Database,
    private val studentService: StudentService,
    private val subjectService: SubjectService,
    private val gson: Gson,
) : StudentSubjectService {
    private val listOfSubjectInfoType = object : TypeToken<List<SubjectInfo>>() {}.type

    override suspend fun getAllStudentSubjects(): List<StudentSubject>? {
        val results = db.from(StudentSubjectEntity)
            .select()
            .map {
                val uniqueStudentId = it[StudentSubjectEntity.uniqueStudentId] ?: emptyString
                val uniqueSubjectId = it[StudentSubjectEntity.uniqueSubjectId] ?: emptyString
                StudentSubject(uniqueStudentId, uniqueSubjectId)
            }
        return results.ifEmpty { null }
    }

    override suspend fun getAllStudentsOfThisSubject(uniqueSubjectId: String): List<StudentInfo>? {
        val results = db.from(StudentSubjectEntity)
            .select()
            .where { StudentSubjectEntity.uniqueSubjectId eq uniqueSubjectId }
            .map { it[StudentSubjectEntity.uniqueStudentId] }
        val students = mutableListOf<StudentInfo>()
        results.forEach { result->
            val student = result?.let { studentService.getStudents(it, UniqueStudentId)?.firstOrNull() }
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

    override suspend fun getAllSubjectsOfThisStudent(uniqueStudentId: String): List<SubjectInfo>? {
        val results = db.from(StudentSubjectEntity)
            .select()
            .where { StudentSubjectEntity.uniqueStudentId eq uniqueStudentId }
            .map { it[StudentSubjectEntity.uniqueStudentId] }
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

    override suspend fun addStudentSubject(studentSubject: StudentSubject): StudentSubject? {
        val uniqueStudentId = studentSubject.uniqueStudentId
        val uniqueSubjectId = studentSubject.uniqueSubjectId
        val result =  db.insert(StudentSubjectEntity){
            set(it.uniqueStudentId, studentSubject.uniqueStudentId)
            set(it.uniqueSubjectId, studentSubject.uniqueSubjectId)
        }
        return if (result < 1){
            null
        }else{
            val thisStudent = studentService.getStudents(uniqueStudentId, UniqueStudentId)?.firstOrNull()
            val thisSubject = subjectService.getSubject(uniqueSubjectId, UniqueSubjectId)?.firstOrNull()

            val subjectsJSON = thisStudent?.subjects
            val subjectsInfoList: List<SubjectInfo> = gson.fromJson(subjectsJSON, listOfSubjectInfoType)
            val mutableListOfSubjectsInfo = subjectsInfoList.toMutableList()
            thisSubject?.toSubjectInfo()?.let { mutableListOfSubjectsInfo.add(it) }
            val updatedSubjectListJSON = gson.toJson(mutableListOfSubjectsInfo)
            studentService.addTeachers(updatedSubjectListJSON, uniqueStudentId)

            db.from(StudentSubjectEntity)
                .select()
                .where { StudentSubjectEntity.uniqueStudentId eq studentSubject.uniqueStudentId }
                .where { StudentSubjectEntity.uniqueSubjectId eq studentSubject.uniqueSubjectId }
                .map {
                    val thisUniqueStudentId = it[StudentSubjectEntity.uniqueStudentId] ?: emptyString
                    val thisUniqueSubjectId = it[StudentSubjectEntity.uniqueSubjectId] ?: emptyString
                    StudentSubject(thisUniqueStudentId, thisUniqueSubjectId)
                }.firstOrNull()
        }
    }

    override suspend fun updateStudentSubject(
        uniqueStudentId: String,
        uniqueSubjectId: String,
        studentSubject: StudentSubject,
    ): StudentSubject? {
        val rowsAffected = db.update(StudentSubjectEntity){
            set(it.uniqueStudentId, studentSubject.uniqueStudentId)
            set(it.uniqueSubjectId, studentSubject.uniqueSubjectId)
            where { it.uniqueStudentId eq uniqueStudentId }
            where { it.uniqueSubjectId eq uniqueSubjectId }
        }
        return if (rowsAffected < 1){
            null
        }else {

            val updatedUniqueSubjectId = studentSubject.uniqueSubjectId
            val updatedUniqueStudentId = studentSubject.uniqueStudentId

            val oldStudent = studentService.getStudents(uniqueStudentId, UniqueStudentId)?.firstOrNull()

            val updatedStudent = studentService.getStudents(updatedUniqueStudentId, UniqueStudentId)?.firstOrNull()
            val updatedSubject = subjectService.getSubject(updatedUniqueStudentId, UniqueSubjectId)?.firstOrNull()

            val subjectsOfOldStudentJSON = oldStudent?.subjects
            val listOf_SubjectInfo_Of_OldStudent: List<SubjectInfo> = gson.fromJson(subjectsOfOldStudentJSON, listOfSubjectInfoType)
            val mutableList_Of_SubjectInfo_Of_OldStudent = listOf_SubjectInfo_Of_OldStudent.toMutableList()
            mutableList_Of_SubjectInfo_Of_OldStudent.removeIf { it.uniqueSubjectId == uniqueSubjectId }
            val updatedList_Of_Subjects_Of_OldStudent_JSON = gson.toJson(mutableList_Of_SubjectInfo_Of_OldStudent)
            studentService.addSubjects(updatedList_Of_Subjects_Of_OldStudent_JSON, uniqueStudentId)

            val subjectsOfUpdatedStudentJSON = updatedStudent?.subjects
            val listOf_SubjectInfo_Of_UpdatedStudent: List<SubjectInfo> = gson.fromJson(subjectsOfUpdatedStudentJSON, listOfSubjectInfoType)
            val mutableList_Of_SubjectInfo_Of_UpdatedStudent = listOf_SubjectInfo_Of_UpdatedStudent.toMutableList()
            mutableList_Of_SubjectInfo_Of_UpdatedStudent.removeIf { it.uniqueSubjectId == updatedUniqueSubjectId }
            updatedSubject?.toSubjectInfo()?.let { mutableList_Of_SubjectInfo_Of_UpdatedStudent.add(it) }
            val updatedList_Of_Subjects_Of_UpdatedStudent_JSON = gson.toJson(mutableList_Of_SubjectInfo_Of_OldStudent)
            studentService.addSubjects(updatedList_Of_Subjects_Of_UpdatedStudent_JSON, uniqueStudentId)


            db.from(StudentSubjectEntity)
                .select()
                .where { StudentSubjectEntity.uniqueStudentId eq studentSubject.uniqueStudentId }
                .where { StudentSubjectEntity.uniqueSubjectId eq studentSubject.uniqueSubjectId }
                .map {
                    val thisUniqueStudentId = it[StudentSubjectEntity.uniqueStudentId] ?: emptyString
                    val thisUniqueSubjectId = it[StudentSubjectEntity.uniqueSubjectId] ?: emptyString
                    StudentSubject(thisUniqueStudentId, thisUniqueSubjectId)
                }.firstOrNull()
        }
    }

    override suspend fun deleteStudentSubject(studentSubject: StudentSubject): Int {
        return db.delete(StudentSubjectEntity) {
            StudentSubjectEntity.uniqueSubjectId eq studentSubject.uniqueSubjectId
            StudentSubjectEntity.uniqueStudentId eq studentSubject.uniqueStudentId
        }
    }
}