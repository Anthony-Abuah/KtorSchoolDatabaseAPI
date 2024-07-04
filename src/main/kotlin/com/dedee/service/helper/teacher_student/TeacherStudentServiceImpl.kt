package com.dedee.service.helper.teacher_student

import com.dedee.entities.TeacherStudentEntity
import com.dedee.model.helper_tables.TeacherStudent
import com.dedee.model.student.StudentInfo
import com.dedee.model.teacher.TeacherInfo
import com.dedee.service.student.StudentService
import com.dedee.service.teacher.TeacherService
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.ktorm.database.Database
import org.ktorm.dsl.*
import util.Constants.emptyString
import util.DatabaseEntityFields.UniqueStudentId

class TeacherStudentServiceImpl(
    private val db: Database,
    private val teacherService: TeacherService,
    private val studentService: StudentService,
    private val gson: Gson,
) : TeacherStudentService {
    private val listOfStudentInfoType = object : TypeToken<List<StudentInfo>>() {}.type
    private val listOfTeachersInfoType = object : TypeToken<List<TeacherInfo>>() {}.type
    override suspend fun getAllTeacherStudents(): List<TeacherStudent>? {
        val result = db.from(TeacherStudentEntity)
            .select()
            .map {
                val uniqueTeacherId = it[TeacherStudentEntity.uniqueTeacherId] ?: emptyString
                val uniqueStudentId = it[TeacherStudentEntity.uniqueStudentId] ?: emptyString
                TeacherStudent(uniqueTeacherId, uniqueStudentId)
            }
        return result.ifEmpty { null }
    }

    override suspend fun getAllTeachersOfThisStudent(uniqueStudentId: String): List<TeacherInfo>? {
        val uniqueTeacherIds = db.from(TeacherStudentEntity)
            .select()
            .where { TeacherStudentEntity.uniqueStudentId eq uniqueStudentId }
            .map { it[TeacherStudentEntity.uniqueTeacherId] }
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

    override suspend fun getAllStudentsOfThisTeacher(uniqueTeacherId: String): List<StudentInfo>? {
        val uniqueTeacherIds = db.from(TeacherStudentEntity)
            .select()
            .where { TeacherStudentEntity.uniqueTeacherId eq uniqueTeacherId }
            .map { it[TeacherStudentEntity.uniqueTeacherId] }
        val students = mutableListOf<StudentInfo>()
        uniqueTeacherIds.forEach { teacherId->
            val student = teacherId?.let { studentService.getStudents(it, UniqueStudentId)?.firstOrNull() }
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

    override suspend fun addTeacherStudent(teacherStudent: TeacherStudent): TeacherStudent? {
        val uniqueTeacherId = teacherStudent.uniqueTeacherId
        val uniqueStudentId = teacherStudent.uniqueStudentId
        val result =  db.insert(TeacherStudentEntity){
            set(it.uniqueTeacherId, teacherStudent.uniqueTeacherId)
            set(it.uniqueStudentId, teacherStudent.uniqueStudentId)
        }
        return if (result < 1){
            null
        }else{
            val thisTeacher = teacherService.findTeacherByUniqueTeacherId(uniqueTeacherId)
            val thisStudent = studentService.getStudents(uniqueStudentId, UniqueStudentId)?.firstOrNull()

            val studentsJSON = thisTeacher?.students
            val studentsInfoList: List<StudentInfo> = gson.fromJson(studentsJSON, listOfStudentInfoType)
            val mutableListOfStudentsInfo = studentsInfoList.toMutableList()
            thisStudent?.toStudentInfo()?.let { mutableListOfStudentsInfo.add(it) }
            val updatedStudentListJSON = gson.toJson(mutableListOfStudentsInfo)
            teacherService.addStudents(updatedStudentListJSON, uniqueTeacherId)

            val teachersJSON = thisStudent?.teachers
            val teachersInfoList: List<TeacherInfo> = gson.fromJson(teachersJSON, listOfTeachersInfoType)
            val mutableListOfTeachersInfo = teachersInfoList.toMutableList()
            thisTeacher?.toTeacherInfo()?.let { mutableListOfTeachersInfo.add(it) }
            val updatedTeacherListJSON = gson.toJson(mutableListOfStudentsInfo)
            studentService.addTeachers(updatedTeacherListJSON, uniqueTeacherId)

            db.from(TeacherStudentEntity)
                .select()
                .where { TeacherStudentEntity.uniqueTeacherId eq teacherStudent.uniqueTeacherId }
                .where { TeacherStudentEntity.uniqueStudentId eq teacherStudent.uniqueStudentId }
                .map {
                    val thisUniqueTeacherId = it[TeacherStudentEntity.uniqueTeacherId] ?: emptyString
                    val thisUniqueStudentId = it[TeacherStudentEntity.uniqueStudentId] ?: emptyString
                    TeacherStudent(thisUniqueTeacherId, thisUniqueStudentId)
                }.firstOrNull()
        }
    }

    override suspend fun updateTeacherStudent(uniqueTeacherId: String, uniqueStudentId: String, teacherStudent: TeacherStudent): TeacherStudent? {
        val rowsAffected = db.update(TeacherStudentEntity){
            set(it.uniqueTeacherId, teacherStudent.uniqueTeacherId)
            set(it.uniqueStudentId, teacherStudent.uniqueStudentId)
            where { it.uniqueTeacherId eq uniqueTeacherId }
            where { it.uniqueStudentId eq uniqueStudentId }
        }
        return if (rowsAffected < 1){
            null
        }else {
            db.from(TeacherStudentEntity)
                .select()
                .where { TeacherStudentEntity.uniqueTeacherId eq teacherStudent.uniqueTeacherId }
                .where { TeacherStudentEntity.uniqueStudentId eq teacherStudent.uniqueStudentId }
                .map {
                    val thisUniqueTeacherId = it[TeacherStudentEntity.uniqueTeacherId] ?: emptyString
                    val thisUniqueStudentId = it[TeacherStudentEntity.uniqueStudentId] ?: emptyString
                    TeacherStudent(thisUniqueTeacherId, thisUniqueStudentId)
                }.firstOrNull()
        }
    }

    override suspend fun deleteTeacherStudent(teacherStudent: TeacherStudent): Int {
        return db.delete(TeacherStudentEntity) {
            TeacherStudentEntity.uniqueStudentId eq teacherStudent.uniqueStudentId
            TeacherStudentEntity.uniqueTeacherId eq teacherStudent.uniqueTeacherId
        }
    }

}