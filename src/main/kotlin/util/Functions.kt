package util

import util.Constants.emptyString
import util.DatabaseEntityFields.AdminUsername
import util.DatabaseEntityFields.AdministratorId
import util.DatabaseEntityFields.Email
import java.util.*

object Functions {

    fun adminUsernameGenerator(firstName: String, lastName: String): String{
        val nameLength = firstName.plus(lastName).trim().length
        val capFirstName = firstName.take(1).uppercase()
        val capLastName = lastName.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
        return "Admin_${capLastName}_${capFirstName}$nameLength"
    }

    fun uniqueStudentIdGenerator(firstName: String, lastName: String): String{
        val capFirstName = firstName.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
        val capLastName = lastName.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
        var randomNumber = emptyString
        for (number in  1..5){
            randomNumber = randomNumber.plus(((0..9).shuffled().first().toString()))
        }
        return "Student_${capLastName}_${capFirstName}_$randomNumber"
    }

    fun parentUsernameGenerator(firstName: String, lastName: String): String{
        val capFirstName = firstName.take(1).uppercase()
        val capLastName = lastName.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
        var randomNumber = emptyString
        for (number in  1..5){
            randomNumber = randomNumber.plus(((0..9).shuffled().first().toString()))
        }
        return "Parent_${capLastName}_${capFirstName}$randomNumber"
    }

    fun uniqueTeacherIdGenerator(firstName: String, lastName: String): String{
        val capLastName = lastName.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
        val capFirstName = firstName.take(1).uppercase()
        var randomNumber = emptyString
        for (number in  1..5){
            randomNumber = randomNumber.plus(((0..9).shuffled().first().toString()))
        }
        return "Teacher_${capLastName}_$capFirstName${randomNumber}"
    }
    fun uniqueSubjectIdGenerator(subjectName: String): String{
        val capSubjectName = subjectName.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
        var randomNumber = emptyString
        for (number in  1..5){
            randomNumber = randomNumber.plus(((0..9).shuffled().first().toString()))
        }
        return "Subject_${capSubjectName}_${randomNumber}"
    }
    fun uniqueClassIdGenerator(className: String, academicYear: String): String{
        val capClassName = className.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
        val capAcademicYear = academicYear.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
        var randomNumber = emptyString
        for (number in  1..5){
            randomNumber = randomNumber.plus(((0..9).shuffled().first().toString()))
        }
        return "Class_${capClassName}_${capAcademicYear}_${randomNumber}"
    }
    fun uniqueGradingIdGenerator(uniqueStudentId: String, uniqueSubjectId: String): String{
        val capSubjectName = uniqueSubjectId.removeSuffix(uniqueSubjectId.takeLast(5))
        val capStudentName = uniqueStudentId.removeSuffix(uniqueStudentId.takeLast(5))
        var randomNumber = emptyString
        for (number in  1..5){
            randomNumber = randomNumber.plus(((0..9).shuffled().first().toString()))
        }
        return "Grading_${capStudentName}_${capSubjectName}_${randomNumber}"
    }
    fun uniqueScoreIdGenerator(uniqueStudentId: String, uniqueSubjectId: String, uniqueTermName: String): String{
        val capTermName = uniqueTermName.removeSuffix(uniqueTermName.takeLast(5))
        val capSubjectName = uniqueSubjectId.removeSuffix(uniqueSubjectId.takeLast(5))
        val capStudentName = uniqueStudentId.removeSuffix(uniqueStudentId.takeLast(5))
        var randomNumber = emptyString
        for (number in  1..5){
            randomNumber = randomNumber.plus(((0..9).shuffled().first().toString()))
        }
        return "Score_${capStudentName}_${capSubjectName}_${capTermName}_${randomNumber}"
    }
    fun uniqueDailyAttendanceIdGenerator(uniqueStudentId: String, uniqueClassId: String, uniqueTermName: String): String{
        val capTermName = uniqueTermName.removeSuffix(uniqueTermName.takeLast(5))
        val capClassId = uniqueClassId.removeSuffix(uniqueClassId.takeLast(5))
        val capStudentName = uniqueStudentId.removeSuffix(uniqueStudentId.takeLast(5))
        var randomNumber = emptyString
        for (number in  1..5){
            randomNumber = randomNumber.plus(((0..9).shuffled().first().toString()))
        }
        return "DailyAttendance_${capStudentName}_${capClassId}_${capTermName}_${randomNumber}"
    }

    fun uniqueConductIdGenerator(uniqueStudentId: String, uniqueClassId: String, uniqueTermName: String): String{
        val capTermName = uniqueTermName.removeSuffix(uniqueTermName.takeLast(5))
        val capClassName = uniqueClassId.removeSuffix(uniqueClassId.takeLast(5))
        val capStudentName = uniqueStudentId.removeSuffix(uniqueStudentId.takeLast(5))
        var randomNumber = emptyString
        for (number in  1..5){
            randomNumber = randomNumber.plus(((0..9).shuffled().first().toString()))
        }
        return "Conduct_${capStudentName}_${capClassName}_${capTermName}_${randomNumber}"
    }
    fun uniqueAttendanceIdGenerator(uniqueStudentId: String, uniqueTermName: String): String{
        val capTermName = uniqueTermName.removeSuffix(uniqueTermName.takeLast(5))
        val capStudentName = uniqueStudentId.removeSuffix(uniqueStudentId.takeLast(5))
        var randomNumber = emptyString
        for (number in  1..5){
            randomNumber = randomNumber.plus(((0..9).shuffled().first().toString()))
        }
        return "Attendance_${capStudentName}_${capTermName}_${randomNumber}"
    }
    fun uniqueTermIdGenerator(termName: String): String{
        val capTermName = (termName.removeSuffix(termName.takeLast(5))).replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
        var randomNumber = emptyString
        for (number in  1..5){
            randomNumber = randomNumber.plus(((0..9).shuffled().first().toString()))
        }
        return "Term_${capTermName}_${randomNumber}"
    }

    fun uniqueNoticeIdGenerator(title: String): String{
        val capTitle = (title.random() + title.take(5)).replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
        var randomNumber = emptyString
        for (number in  1..5){
            randomNumber = randomNumber.plus(((0..9).shuffled().first().toString()))
        }
        return "Notice_${capTitle}_${randomNumber}"
    }
    fun uniqueTimeTableIdGenerator(timeTableType: String, subjectId: String, classId: String): String{
        val capTimeTableType = (timeTableType.random() + timeTableType.take(5)).replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
        val capSubject = (subjectId.removeSuffix(subjectId.takeLast(5))).replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
        val capClass = (classId.removeSuffix(classId.takeLast(5))).replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }

        var randomNumber = emptyString
        for (number in  1..5){
            randomNumber = randomNumber.plus(((0..9).shuffled().first().toString()))
        }
        return "TimeTable_${capTimeTableType}_${capSubject}_${capClass}_${randomNumber}"
    }

    fun getAdminParameterType(parameter: String): String{
        return try {
            if(parameter.toDouble().isNaN()){
                if (parameter.contains("@")){
                    Email
                }
                else AdminUsername
            }else{
                AdministratorId
            }
        }catch (e: NumberFormatException){
            if (parameter.contains("@")){
                Email
            }
            else AdminUsername
        }
    }


}