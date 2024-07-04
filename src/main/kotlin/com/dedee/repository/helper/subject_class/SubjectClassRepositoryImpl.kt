package com.dedee.repository.helper.subject_class

import com.dedee.model.helper_tables.SubjectClass
import com.dedee.model.school_class.SchoolClassInfo
import com.dedee.model.subject.SubjectInfo
import com.dedee.service.helper.subject_class.SubjectClassService
import util.ResponseFeedback

class SubjectClassRepositoryImpl(
    private val subjectClassService: SubjectClassService,
) : SubjectClassRepository{
    override suspend fun getAllSubjectClasses(): ResponseFeedback<List<SubjectClass>?> {
        val result = subjectClassService.getAllSubjectClasses()
        return if (result != null){
            ResponseFeedback(
                data = result,
                message = "Data has been fetched successfully",
                success = true
            )
        }
        else{
            ResponseFeedback(
                data = null,
                message = "Unable to load data",
                success = false
            )
        }
    }

    override suspend fun getAllSubjectsOfThisClass(uniqueClassId: String): ResponseFeedback<List<SubjectInfo>?> {
        val subjectInfo = subjectClassService.getAllSubjectsOfThisClass(uniqueClassId)
        return if (subjectInfo != null){
            ResponseFeedback(
                data = subjectInfo,
                message = "Data has been fetched successfully",
                success = true
            )
        }
        else{
            ResponseFeedback(
                data = null,
                message = "Unable to load data",
                success = false
            )
        }
    }

    override suspend fun getAllClassesOfThisSubject(uniqueSubjectId: String): ResponseFeedback<List<SchoolClassInfo>?> {
        val parentInfo = subjectClassService.getAllClassesOfThisSubject(uniqueSubjectId)
        return if (parentInfo != null){
            ResponseFeedback(
                data = parentInfo,
                message = "Data has been fetched successfully",
                success = true
            )
        }
        else{
            ResponseFeedback(
                data = null,
                message = "Unable to load data",
                success = false
            )
        }
    }

    override suspend fun addSubjectClass(subjectClass: SubjectClass): ResponseFeedback<SubjectClass?> {
        val _subjectClass = subjectClassService.addSubjectClass(subjectClass)
        return if (_subjectClass != null){
            ResponseFeedback(
                data = _subjectClass,
                message = "Data has been fetched successfully",
                success = true
            )
        }
        else{
            ResponseFeedback(
                data = null,
                message = "Unable to load data",
                success = false
            )
        }
    }

    override suspend fun updateSubjectClass(
        uniqueSubjectId: String,
        uniqueClassId: String,
        subjectClass: SubjectClass,
    ): ResponseFeedback<SubjectClass?> {
        val _updatedSubjectClass = subjectClassService.updateSubjectClass(uniqueSubjectId, uniqueClassId, subjectClass)
        return if (_updatedSubjectClass != null){
            ResponseFeedback(
                data = _updatedSubjectClass,
                message = "Data has been updated successfully",
                success = true
            )
        }
        else{
            ResponseFeedback(
                data = null,
                message = "Unable to update data",
                success = false
            )
        }
    }

    override suspend fun deleteSubjectClass(subjectClass: SubjectClass): ResponseFeedback<String> {
        val deletedRows = subjectClassService.deleteSubjectClass(subjectClass)
        return if (deletedRows != null && deletedRows  > 0){
            ResponseFeedback(
                data = "Data deleted successfully",
                message = "Data deleted successfully",
                success = true
            )
        }
        else{
            ResponseFeedback(
                data = null,
                message = "Unable to delete data",
                success = false
            )
        }
    }
}