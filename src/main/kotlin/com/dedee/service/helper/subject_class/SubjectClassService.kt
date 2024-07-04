package com.dedee.service.helper.subject_class

import com.dedee.model.helper_tables.SubjectClass
import com.dedee.model.school_class.SchoolClassInfo
import com.dedee.model.subject.SubjectInfo

interface SubjectClassService {

    suspend fun getAllSubjectClasses(): List<SubjectClass>?

    suspend fun getAllSubjectsOfThisClass(uniqueClassId: String): List<SubjectInfo>?

    suspend fun getAllClassesOfThisSubject(uniqueSubjectId: String): List<SchoolClassInfo>?

    suspend fun addSubjectClass(subjectClass: SubjectClass): SubjectClass?

    suspend fun updateSubjectClass(uniqueSubjectId: String, uniqueClassId: String, subjectClass: SubjectClass): SubjectClass?

    suspend fun deleteSubjectClass(subjectClass: SubjectClass): Int?

}