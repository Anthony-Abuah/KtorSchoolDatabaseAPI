package util

object DatabaseEntityFields {

    //AdministratorEntity
    const val AdministratorId = "administratorId"
    const val FirstName = "firstName"
    const val LastName = "lastName"
    const val AdminUsername = "adminUsername"
    const val Password = "password"
    const val Salt = "salt"
    const val Email = "email"
    const val Position = "position"
    const val Gender = "gender"

    // ParentEntity
    const val ParentUsername = "parentUsername"
    const val OtherNames = "otherNames"
    const val Wards = "wards"
    const val Ward = "ward"
    const val Contact = "contact"
    const val HasAnEnrolledWard = "hasAnEnrolledWard"
    const val IsStillAtThisSchool = "isStillAtThisSchool"

    // TeacherEntity
    const val TeacherId = "teacherUsername"
    const val TeacherUsername = "teacherUsername"
    const val UniqueTeacherId = "uniqueTeacherId"
    const val Classes = "classes"
    const val Subjects = "subjects"

    // StudentEntity
    const val UniqueStudentId = "uniqueStudentId"
    const val DateOfBirth = "dateOfBirth"
    const val IsEnrolled = "isEnrolled"
    const val Teachers = "teachers"
    const val Photo = "photo"
    const val Parents = "parents"
    const val Gradings = "gradings"
    const val Conducts = "conducts"

    // ClassEntity
    const val UniqueClassId = "uniqueClassId"
    const val ClassName = "className"
    const val Students = "students"
    const val ClassStage = "classStage"
    const val AcademicYear = "academicYear"
    const val NumberOfBoys = "numberOfBoys"
    const val NumberOfGirls = "numberOfGirls"
    const val TotalNumberOfStudents = "totalNumberOfStudents"

    // SubjectEntity
    const val SubjectName = "subjectName"
    const val UniqueSubjectId = "uniqueSubjectId"


    // TermEntity
    const val TermName = "termName"
    const val UniqueTermName = "uniqueTermName"
    const val Year = "year"
    const val StartDate = "startDate"
    const val EndDate = "endDate"
    const val NumberOfDays = "numberOfDays"


    // TermEntity
    const val UniqueGradingId = "uniqueGradingId"
    const val FinalGrade = "finalGrade"
    const val TeacherRemarks = "teacherRemarks"
    const val GradeRemarks = "gradeRemarks"
    const val Scores = "scores"


    // ScoreEntity
    const val UniqueScoreId = "uniqueScoreId"
    const val ScoreType = "scoreType"
    const val Marks = "marks"
    const val Grade = "grade"
    const val Remarks = "remarks"
    const val ClassAverage = "classAverage"


    // ConductEntity
    const val UniqueConductId = "uniqueConductId"
    const val Conduct = "conduct"
    const val Interests = "interests"

    // ConductEntity
    const val UniqueDailyAttendanceId = "uniqueDailyAttendanceId"
    const val Date = "date"
    const val IsPresent = "isPresent"



    // AttendanceEntity
    const val UniqueAttendanceId = "uniqueAttendanceId"
    const val DailyAttendances = "attendanceDates"
    const val AbsentDates = "absentDates"
    const val NumberOfAttendedDays = "numberOfAttendedDays"
    const val NumberOfAbsentDays = "numberOfAbsentDays"
    const val NumberOfSchoolDays = "numberOfSchoolDays"


    // TimeTableEntity
    const val UniqueTimeTableId = "uniqueTimeTableId"
    const val Days = "days"
    const val StartTime = "startTime"
    const val EndTime = "endTime"
    const val TimeTableType = "timeTableType"


    // NoticeBoardEntity
    const val UniqueNoticeId = "uniqueNoticeId"
    const val Title = "title"
    const val Message = "message"
    const val Priority = "priority"
    const val NoticeTime = "noticeTime"








}