package util

import util.Constants.currentPassword
import util.Constants.parameter
import util.Constants.parameterType
import util.Constants.updatedPassword
import util.DatabaseEntityFields.AdminUsername
import util.DatabaseEntityFields.Email
import util.DatabaseEntityFields.ParentUsername
import util.DatabaseEntityFields.TeacherUsername
import util.DatabaseEntityFields.UniqueAttendanceId
import util.DatabaseEntityFields.UniqueClassId
import util.DatabaseEntityFields.UniqueConductId
import util.DatabaseEntityFields.UniqueDailyAttendanceId
import util.DatabaseEntityFields.UniqueGradingId
import util.DatabaseEntityFields.UniqueNoticeId
import util.DatabaseEntityFields.UniqueScoreId
import util.DatabaseEntityFields.UniqueStudentId
import util.DatabaseEntityFields.UniqueSubjectId
import util.DatabaseEntityFields.UniqueTeacherId
import util.DatabaseEntityFields.UniqueTermName
import util.DatabaseEntityFields.UniqueTimeTableId

object Routes {

    //Admin Routes
    const val getAllAdmins = "/getAllAdmins"
    const val changeAdminPassword = "/changeAdminPassword/{$parameter}/{$currentPassword}/{$updatedPassword}"
    const val getAdminByEmail = "/getAdminByEmail/{$Email}"
    const val getAdminByAdminUsername = "/getAdminByUsername/{$AdminUsername}"
    const val registerAdmin = "/registerAdmin"
    const val updateAdmin = "/updateAdmin/{$parameter}"
    const val deleteAdmin = "/deleteAdmin/{$parameter}"

    //Parent Routes
    const val getAllParents = "/getAllParents"
    const val changeParentPasswords = "/changeParentPassword/{$parameter}/{$currentPassword}/{$updatedPassword}"
    const val getParentByEmail = "/getParentByEmail/{$Email}"
    const val getParentByParentUsername = "/getParentByUsername/{$ParentUsername}"
    const val registerParent = "/registerParent"
    const val updateParent = "/updateParent/{$parameter}"
    const val deleteParent = "/deleteParent/{$parameter}"

    //Teacher Routes
    const val getAllTeachers = "/getAllTeachers"
    const val changeTeacherPasswords = "/changeTeacherPassword/{$parameter}/{$currentPassword}/{$updatedPassword}"
    const val getTeacherByEmail = "/getTeacherByEmail/{$Email}"
    const val getTeacherByTeacherUsername = "/getTeacherByUsername/{$TeacherUsername}"
    const val registerTeacher = "/registerTeacher"
    const val updateTeacher = "/updateTeacher/{$parameter}"
    const val deleteTeacher = "/deleteTeacher/{$parameter}"


    //Student Routes
    const val getAllStudents = "/getAllStudents"
    const val getStudents = "/getStudents/{$parameter}/{$parameterType}"
    const val registerStudent = "/registerStudent"
    const val updateStudent = "/updateStudent/{$parameter}"
    const val deleteStudent = "/deleteStudent/{$parameter}"


    //Subject Routes
    const val getAllSubjects = "/getAllSubjects"
    const val getSubjects = "/getSubjects/{$parameter}/{$parameterType}"
    const val registerSubject = "/registerSubject"
    const val updateSubject = "/updateSubject/{$parameter}"
    const val deleteSubject = "/deleteSubject/{$parameter}"

    //Conduct Routes
    const val getAllConducts = "/getAllConducts"
    const val getConduct = "/getConduct/{$UniqueConductId}"
    const val registerConduct = "/registerConduct"
    const val updateConduct = "/updateConduct/{$UniqueConductId}"
    const val deleteConduct = "/deleteConduct/{$UniqueConductId}"

    //Score Routes
    const val getAllScores = "/getAllScores"
    const val getScore = "/getScore/{$UniqueScoreId}"
    const val registerScore = "/registerScore"
    const val updateScore = "/updateScore/{$UniqueScoreId}"
    const val deleteScore = "/deleteScore/{$UniqueScoreId}"

    //DailyAttendance Routes
    const val getAllDailyAttendances = "/getAllDailyAttendances"
    const val getDailyAttendance = "/getDailyAttendance/{$UniqueDailyAttendanceId}"
    const val registerDailyAttendance = "/registerDailyAttendance"
    const val updateDailyAttendance = "/updateDailyAttendance/{$UniqueDailyAttendanceId}"
    const val deleteDailyAttendance = "/deleteDailyAttendance/{$UniqueDailyAttendanceId}"

    //Attendance Routes
    const val getAllAttendances = "/getAllAttendances"
    const val getAttendance = "/getAttendance/{$UniqueAttendanceId}"
    const val registerAttendance = "/registerAttendance"
    const val updateAttendance = "/updateAttendance/{$UniqueAttendanceId}"
    const val deleteAttendance = "/deleteAttendance/{$UniqueAttendanceId}"

    //Grading Routes
    const val getAllGradings = "/getAllGradings"
    const val getGrading = "/getGrading/{$UniqueGradingId}"
    const val getClassGradings = "/getClassGradings/{$UniqueClassId}/{$UniqueSubjectId}/{$UniqueTermName}"
    const val getStudentGradings = "/getStudentGradings/{$UniqueStudentId}/{$UniqueClassId}/{$UniqueSubjectId}/{$UniqueTermName}"
    const val registerGrading = "/registerGrading"
    const val updateGrading = "/updateGrading/{$parameter}"
    const val deleteGrading = "/deleteGrading/{$parameter}"

    //Term Routes
    const val getAllTerms = "/getAllTerms"
    const val getTerms = "/getTerms/{$parameter}/{$parameterType}"
    const val registerTerm = "/registerTerm"
    const val updateTerm = "/updateTerm/{$parameter}"
    const val deleteTerm = "/deleteTerm/{$parameter}"


    //Class Routes
    const val getAllClasses = "/getAllClasses"
    const val getClasses = "/getClasses/{$parameter}/{$parameterType}"
    const val registerClass = "/registerClass"
    const val updateClass = "/updateClass/{$parameter}"
    const val deleteClass = "/deleteClass/{$parameter}"


    //StudentParent Routes

    const val updatedUniqueStudentId = "updatedUniqueStudentId"
    const val updatedParentUsername = "updatedParentUsername"
    const val getAllStudentParents = "/getAllStudentParents"
    const val getParentsOfThisStudent = "/getParentsOfThisStudent/{$UniqueStudentId}"
    const val getStudentsOfThisParent = "/getStudentsOfThisParent/{$ParentUsername}"
    const val addStudentParent = "/addStudentParent"
    const val updateStudentParent = "/updateStudentParent/{$UniqueStudentId}/{$ParentUsername}/{$updatedUniqueStudentId}/{$updatedParentUsername}"
    const val deleteStudentParent = "/deleteStudentParent/{$UniqueStudentId}/{$ParentUsername}"


    //SubjectClass Routes

    const val updatedSubjectId = "updatedSubjectId"
    const val updatedClassId = "updatedClassId"
    const val getAllSubjectClasses = "/getAllSubjectClasses"
    const val getClassesOfThisSubject = "/getClassesOfThisSubject/{$UniqueSubjectId}"
    const val getSubjectsOfThisClass = "/getSubjectsOfThisClass/{$UniqueClassId}"
    const val addSubjectClass = "/addSubjectClass"
    const val updateSubjectClass = "/updateSubjectClass/{$UniqueSubjectId}/{$UniqueClassId}/{$updatedSubjectId}/{$updatedClassId}"
    const val deleteSubjectClass = "/deleteSubjectClass/{$UniqueSubjectId}/{$UniqueClassId}"


    //TeacherClass Routes
    const val updatedTeacherId = "updatedTeacherId"
    const val getAllTeacherClasses = "/getAllTeacherClasses"
    const val getClassesOfThisTeacher = "/getClassesOfThisTeacher/{$UniqueTeacherId}"
    const val getTeachersOfThisClass = "/getTeachersOfThisClass/{$UniqueClassId}"
    const val addTeacherClass = "/addTeacherClass"
    const val updateTeacherClass = "/updateTeacherClass/{$UniqueTeacherId}/{$UniqueClassId}/{$updatedTeacherId}/{$updatedClassId}"
    const val deleteTeacherClass = "/deleteTeacherClass/{$UniqueTeacherId}/{$UniqueClassId}"


    //TeacherStudent Routes
    const val updatedStudentId = "updatedStudentId"
    const val getAllTeacherStudents = "/getAllTeacherStudents"
    const val getStudentsOfThisTeacher = "/getStudentsOfThisTeacher/{$UniqueTeacherId}"
    const val getTeachersOfThisStudent = "/getTeachersOfThisStudent/{$UniqueStudentId}"
    const val addTeacherStudent = "/addTeacherStudent"
    const val updateTeacherStudent = "/updateTeacherStudent/{$UniqueTeacherId}/{$UniqueStudentId}/{$updatedTeacherId}/{$updatedStudentId}"
    const val deleteTeacherStudent = "/deleteTeacherStudent/{$UniqueTeacherId}/{$UniqueStudentId}"


    //TeacherSubject Routes
    const val getAllTeacherSubjects = "/getAllTeacherSubjects"
    const val getSubjectsOfThisTeacher = "/getSubjectsOfThisTeacher/{$UniqueTeacherId}"
    const val getTeachersOfThisSubject = "/getTeachersOfThisSubject/{$UniqueSubjectId}"
    const val addTeacherSubject = "/addTeacherSubject"
    const val updateTeacherSubject = "/updateTeacherSubject/{$UniqueTeacherId}/{$UniqueSubjectId}/{$updatedTeacherId}/{$updatedSubjectId}"
    const val deleteTeacherSubject = "/deleteTeacherSubject/{$UniqueTeacherId}/{$UniqueSubjectId}"


    //StudentSubject Routes
    const val getAllStudentSubjects = "/getAllStudentSubjects"
    const val getSubjectsOfThisStudent = "/getSubjectsOfThisStudent/{$UniqueStudentId}"
    const val getStudentsOfThisSubject = "/getStudentsOfThisSubject/{$UniqueSubjectId}"
    const val addStudentSubject = "/addStudentSubject"
    const val updateStudentSubject = "/updateStudentSubject/{$UniqueStudentId}/{$UniqueSubjectId}/{$updatedStudentId}/{$updatedSubjectId}"
    const val deleteStudentSubject = "/deleteStudentSubject/{$UniqueStudentId}/{$UniqueSubjectId}"


    //TimeTable Routes
    const val getAllTimeTables = "/getAllTimeTables"
    const val getTimeTable = "/getTimeTable/{$UniqueTimeTableId}"
    const val registerTimeTable = "/registerTimeTable"
    const val updateTimeTable = "/updateTimeTable/{$UniqueTimeTableId}"
    const val deleteTimeTable = "/deleteTimeTable/{$UniqueTimeTableId}"

    //Notice Routes
    const val getAllNotices = "/getAllNotices"
    const val getNotice = "/getNotice/{$UniqueNoticeId}"
    const val registerNotice = "/registerNotice"
    const val updateNotice = "/updateNotice/{$UniqueNoticeId}"
    const val deleteNotice = "/deleteNotice/{$UniqueNoticeId}"


}