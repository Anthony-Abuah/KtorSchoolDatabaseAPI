package com.dedee.plugins

import com.dedee.db.DatabaseConnection
import com.dedee.repository.admin.AdminRepository
import com.dedee.repository.admin.AdminRepositoryImpl
import com.dedee.repository.attendance.AttendanceRepository
import com.dedee.repository.attendance.AttendanceRepositoryImpl
import com.dedee.repository.conduct.ConductRepository
import com.dedee.repository.conduct.ConductRepositoryImpl
import com.dedee.repository.daily_attendance.DailyAttendanceRepository
import com.dedee.repository.daily_attendance.DailyAttendanceRepositoryImpl
import com.dedee.repository.grading.GradingRepository
import com.dedee.repository.grading.GradingRepositoryImpl
import com.dedee.repository.helper.student_parent.StudentParentRepository
import com.dedee.repository.helper.student_parent.StudentParentRepositoryImpl
import com.dedee.repository.helper.student_subject.StudentSubjectRepository
import com.dedee.repository.helper.student_subject.StudentSubjectRepositoryImpl
import com.dedee.repository.helper.subject_class.SubjectClassRepository
import com.dedee.repository.helper.subject_class.SubjectClassRepositoryImpl
import com.dedee.repository.helper.teacher_class.TeacherClassRepository
import com.dedee.repository.helper.teacher_class.TeacherClassRepositoryImpl
import com.dedee.repository.helper.teacher_student.TeacherStudentRepository
import com.dedee.repository.helper.teacher_student.TeacherStudentRepositoryImpl
import com.dedee.repository.helper.teacher_subject.TeacherSubjectRepository
import com.dedee.repository.helper.teacher_subject.TeacherSubjectRepositoryImpl
import com.dedee.repository.notice_board.NoticeBoardRepository
import com.dedee.repository.notice_board.NoticeBoardRepositoryImpl
import com.dedee.repository.parent.ParentRepository
import com.dedee.repository.parent.ParentRepositoryImpl
import com.dedee.repository.school_class.SchoolClassRepository
import com.dedee.repository.school_class.SchoolClassRepositoryImpl
import com.dedee.repository.score.ScoreRepository
import com.dedee.repository.score.ScoreRepositoryImpl
import com.dedee.repository.student.StudentRepository
import com.dedee.repository.student.StudentRepositoryImpl
import com.dedee.repository.subject.SubjectRepository
import com.dedee.repository.subject.SubjectRepositoryImpl
import com.dedee.repository.teacher.TeacherRepository
import com.dedee.repository.teacher.TeacherRepositoryImpl
import com.dedee.repository.term.TermRepository
import com.dedee.repository.term.TermRepositoryImpl
import com.dedee.repository.time_table.TimeTableRepository
import com.dedee.repository.time_table.TimeTableRepositoryImpl
import com.dedee.route.*
import com.dedee.security.hashing.HashService
import com.dedee.security.hashing.HashServiceImpl
import com.dedee.service.admin.AdminService
import com.dedee.service.admin.AdminServiceImpl
import com.dedee.service.attendance.AttendanceService
import com.dedee.service.attendance.AttendanceServiceImpl
import com.dedee.service.conduct.ConductService
import com.dedee.service.conduct.ConductServiceImpl
import com.dedee.service.daily_attendance.DailyAttendanceService
import com.dedee.service.daily_attendance.DailyAttendanceServiceImpl
import com.dedee.service.grading.GradingService
import com.dedee.service.grading.GradingServiceImpl
import com.dedee.service.helper.student_parent.StudentParentService
import com.dedee.service.helper.student_parent.StudentParentServiceImpl
import com.dedee.service.helper.student_subject.StudentSubjectService
import com.dedee.service.helper.student_subject.StudentSubjectServiceImpl
import com.dedee.service.helper.subject_class.SubjectClassService
import com.dedee.service.helper.subject_class.SubjectClassServiceImpl
import com.dedee.service.helper.teacher_class.TeacherClassService
import com.dedee.service.helper.teacher_class.TeacherClassServiceImpl
import com.dedee.service.helper.teacher_student.TeacherStudentService
import com.dedee.service.helper.teacher_student.TeacherStudentServiceImpl
import com.dedee.service.helper.teacher_subject.TeacherSubjectService
import com.dedee.service.helper.teacher_subject.TeacherSubjectServiceImpl
import com.dedee.service.notice_board.NoticeBoardService
import com.dedee.service.notice_board.NoticeBoardServiceImpl
import com.dedee.service.parent.ParentService
import com.dedee.service.parent.ParentServiceImpl
import com.dedee.service.school_class.SchoolClassService
import com.dedee.service.school_class.SchoolClassServiceImpl
import com.dedee.service.score.ScoreService
import com.dedee.service.score.ScoreServiceImpl
import com.dedee.service.student.StudentService
import com.dedee.service.student.StudentServiceImpl
import com.dedee.service.subject.SubjectService
import com.dedee.service.subject.SubjectServiceImpl
import com.dedee.service.teacher.TeacherService
import com.dedee.service.teacher.TeacherServiceImpl
import com.dedee.service.term.TermService
import com.dedee.service.term.TermServiceImpl
import com.dedee.service.time_table.TimeTableService
import com.dedee.service.time_table.TimeTableServiceImpl
import com.google.gson.Gson
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    val db = DatabaseConnection.database
    val gson = Gson()
    val hashService: HashService = HashServiceImpl()
    val adminService: AdminService = AdminServiceImpl(db)
    val parentService: ParentService = ParentServiceImpl(db)
    val teacherService: TeacherService = TeacherServiceImpl(db)
    val schoolClassService: SchoolClassService = SchoolClassServiceImpl(db)
    val studentService: StudentService = StudentServiceImpl(db, gson)
    val subjectService: SubjectService = SubjectServiceImpl(db)
    val gradingService: GradingService = GradingServiceImpl(db)
    val scoreService: ScoreService = ScoreServiceImpl(db)
    val termService: TermService = TermServiceImpl(db)
    val attendanceService: AttendanceService = AttendanceServiceImpl(db)
    val conductService: ConductService = ConductServiceImpl(db)
    val noticeBoardService: NoticeBoardService = NoticeBoardServiceImpl(db)
    val timeTableService: TimeTableService = TimeTableServiceImpl(db)
    val dailyAttendanceService: DailyAttendanceService = DailyAttendanceServiceImpl(db)


    // Helper Services
    val teacherClassService: TeacherClassService = TeacherClassServiceImpl(db, teacherService, schoolClassService, gson)
    val studentParentService: StudentParentService = StudentParentServiceImpl(db, studentService, parentService, gson)
    val studentSubjectService: StudentSubjectService = StudentSubjectServiceImpl(db, studentService, subjectService, gson)
    val subjectClassService: SubjectClassService = SubjectClassServiceImpl(db, subjectService, schoolClassService, gson)
    val teacherStudentService: TeacherStudentService = TeacherStudentServiceImpl(db, teacherService, studentService, gson)
    val teacherSubjectService: TeacherSubjectService = TeacherSubjectServiceImpl(db, teacherService, subjectService, gson)


    val adminRepository: AdminRepository = AdminRepositoryImpl(adminService, hashService)
    val parentRepository: ParentRepository = ParentRepositoryImpl(parentService, hashService)
    val teacherRepository: TeacherRepository = TeacherRepositoryImpl(teacherService, hashService)
    val studentRepository: StudentRepository = StudentRepositoryImpl(studentService,
        teacherClassService,
        subjectClassService)
    val gradingRepository: GradingRepository = GradingRepositoryImpl(gradingService, studentService, gson)
    val termRepository: TermRepository = TermRepositoryImpl(termService)
    val scoreRepository: ScoreRepository = ScoreRepositoryImpl(scoreService, gradingService, gson)
    val conductRepository: ConductRepository = ConductRepositoryImpl(conductService, studentService, gson)
    val attendanceRepository: AttendanceRepository = AttendanceRepositoryImpl(attendanceService)
    val subjectRepository: SubjectRepository = SubjectRepositoryImpl(subjectService)
    val dailyAttendanceRepository: DailyAttendanceRepository = DailyAttendanceRepositoryImpl(dailyAttendanceService, attendanceService, gson)
    val schoolClassRepository: SchoolClassRepository = SchoolClassRepositoryImpl(schoolClassService)
    val timeTableRepository: TimeTableRepository = TimeTableRepositoryImpl(timeTableService)
    val noticeBoardRepository: NoticeBoardRepository = NoticeBoardRepositoryImpl(noticeBoardService)

    val studentParentRepository: StudentParentRepository = StudentParentRepositoryImpl(studentParentService)
    val subjectClassRepository: SubjectClassRepository = SubjectClassRepositoryImpl(subjectClassService)
    val studentSubjectRepository: StudentSubjectRepository = StudentSubjectRepositoryImpl(studentSubjectService)
    val teacherClassRepository: TeacherClassRepository = TeacherClassRepositoryImpl(teacherClassService)
    val teacherStudentRepository: TeacherStudentRepository = TeacherStudentRepositoryImpl(teacherStudentService)
    val teacherSubjectRepository: TeacherSubjectRepository = TeacherSubjectRepositoryImpl(teacherSubjectService)

    adminRoutes(adminRepository)
    parentRoute(parentRepository)
    teacherRoute(teacherRepository)
    studentRoutes(studentRepository)
    subjectRoutes(subjectRepository)
    classRoutes(schoolClassRepository)
    termRoutes(termRepository)
    gradingRoutes(gradingRepository)
    conductRoutes(conductRepository)
    attendanceRoutes(attendanceRepository)
    dailyAttendanceRoutes(dailyAttendanceRepository)
    scoreRoutes(scoreRepository)
    noticeBoardRoutes(noticeBoardRepository)
    timeTableRoutes(timeTableRepository)

    studentParentRoutes(studentParentRepository)
    subjectClassRoute(subjectClassRepository)
    studentSubjectRoute(studentSubjectRepository)
    teacherClassRoute(teacherClassRepository)
    teacherStudentRoute(teacherStudentRepository)
    teacherSubjectRoute(teacherSubjectRepository)

    routing {
        get("/") {
            call.respondText("Hello World!")
        }
    }

}
