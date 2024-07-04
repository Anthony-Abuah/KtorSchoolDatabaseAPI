package com.dedee.repository.helper.subject_class

import com.dedee.model.helper_tables.SubjectClass
import com.dedee.model.school_class.SchoolClassInfo
import com.dedee.model.subject.SubjectInfo
import util.ResponseFeedback


interface SubjectClassRepository {
    suspend fun getAllSubjectClasses(): ResponseFeedback<List<SubjectClass>?>

    suspend fun getAllSubjectsOfThisClass(uniqueClassId: String): ResponseFeedback<List<SubjectInfo>?>

    suspend fun getAllClassesOfThisSubject(uniqueSubjectId: String): ResponseFeedback<List<SchoolClassInfo>?>

    suspend fun addSubjectClass(subjectClass: SubjectClass): ResponseFeedback<SubjectClass?>

    suspend fun updateSubjectClass(uniqueSubjectId: String, uniqueClassId: String, subjectClass: SubjectClass): ResponseFeedback<SubjectClass?>

    suspend fun deleteSubjectClass(subjectClass: SubjectClass): ResponseFeedback<String>

    
}



