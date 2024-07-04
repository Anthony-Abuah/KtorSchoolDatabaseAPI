package com.dedee.repository.conduct

import com.dedee.model.conduct.ConductInfo
import com.dedee.model.conduct.ConductRequest
import com.dedee.model.conduct.ConductResponse
import com.dedee.service.conduct.ConductService
import com.dedee.service.student.StudentService
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import util.Constants.emptyString
import util.DatabaseEntityFields.UniqueStudentId
import util.Functions.uniqueConductIdGenerator
import util.ResponseFeedback

class ConductRepositoryImpl(
    private val conductService: ConductService,
    private val studentService: StudentService,
    private val gson: Gson,
) : ConductRepository{
    override suspend fun getAllConducts(): ResponseFeedback<List<ConductResponse>?> {
        val conductResponse = conductService.getAllConducts()
        return if (conductResponse.isNullOrEmpty()) {
            ResponseFeedback(
                data = null,
                message = "Could not fetch all conducts",
                success = false
            )
        }else{
            ResponseFeedback(
                data = conductResponse,
                message = "All conducts successfully loaded",
                success = true
            )
        }
    }

    override suspend fun addConduct(conduct: ConductRequest): ResponseFeedback<ConductResponse?> {
        val uniqueStudentId = conduct.uniqueStudentId ?: emptyString
        val uniqueClassId = conduct.uniqueClassId ?: emptyString
        val uniqueTermName = conduct.uniqueTermName ?: emptyString
        val uniqueConductId = uniqueConductIdGenerator(uniqueStudentId, uniqueClassId, uniqueTermName)
        return if (uniqueConductIdExists(uniqueConductId)){
            ResponseFeedback(
                data = null,
                message = "Cannot add conduct because it already exists",
                success = false
            )
        }else{
            val registerResponse = conductService.addConduct(conduct, uniqueConductId)
            if (registerResponse < 1 ) {
                ResponseFeedback(
                    data = null,
                    message = "Unable to add conduct",
                    success = false
                )
            }else{
                val thisConduct = conductService.getConduct(uniqueConductId)
                val student = uniqueStudentId.let { studentService.getStudents(it, UniqueStudentId)?.firstOrNull() }
                val conductJSON = student?.conducts
                val listOfConductInfoType = object : TypeToken<List<ConductInfo>>() {}.type
                val conductInfoList: List<ConductInfo> = gson.fromJson(conductJSON, listOfConductInfoType)
                val mutableListOfConductInfo = conductInfoList.toMutableList()
                thisConduct?.toConductInfo()?.let { mutableListOfConductInfo.add(it) }
                val updatedConductListJSON = gson.toJson(mutableListOfConductInfo)
                studentService.addConduct(updatedConductListJSON, uniqueStudentId)
                ResponseFeedback(
                    data = thisConduct,
                    message = "Conduct successfully registered",
                    success = true
                )
            }
        }
    }

    override suspend fun getConduct(
        uniqueConductId: String,
    ): ResponseFeedback<ConductResponse?> {
        val response = conductService.getConduct(uniqueConductId)
        return if (response == null){
            ResponseFeedback(
                data = null,
                message = "No conduct matches the given criteria",
                success = false
            )
        }else{
            ResponseFeedback(
                data = response,
                message = "Conducts fetched successfully",
                success = true
            )
        }
    }

    override suspend fun deleteConduct(uniqueConductId: String): ResponseFeedback<String> {
        val response =  conductService.deleteConduct(uniqueConductId) ?: -1
        return if (response < 1){
            ResponseFeedback(
                data = "Unable to delete conduct",
                message = "Unknown Error",
                success = false
            )
        }else{
            ResponseFeedback(
                data = "Conduct deleted successfully",
                message = "Conduct deleted successfully",
                success = true
            )
        }

    }

    override suspend fun updateConduct(
        uniqueConductId: String,
        updatedConduct: ConductRequest,
    ): ResponseFeedback<ConductResponse?> {
        val thisConduct = conductService.updateConduct(uniqueConductId, updatedConduct)
        return if (thisConduct == null){
            ResponseFeedback(
                data = null,
                message = "Unable to update conduct",
                success = false
            )
        }else{
            //val thisConduct = conductService.getConduct(uniqueConductId)
            val uniqueStudentId = thisConduct.uniqueStudentId ?: emptyString
            val student = uniqueStudentId.let { studentService.getStudents(it, UniqueStudentId)?.firstOrNull() }
            val conductJSON = student?.conducts
            val listOfConductInfoType = object : TypeToken<List<ConductInfo>>() {}.type
            val conductInfoList: List<ConductInfo> = gson.fromJson(conductJSON, listOfConductInfoType)
            val mutableListOfConductInfo = conductInfoList.toMutableList()
            mutableListOfConductInfo.removeIf { it.uniqueConductId == uniqueConductId }
            thisConduct.toConductInfo().let { mutableListOfConductInfo.add(it) }
            val updatedConductListJSON = gson.toJson(mutableListOfConductInfo)
            studentService.addConduct(updatedConductListJSON, uniqueStudentId)
            ResponseFeedback(
                data = thisConduct,
                message = "Conduct updated successfully",
                success = true
            )
        }
    }


    private suspend fun uniqueConductIdExists(uniqueConductId: String): Boolean {
        return conductService.getConduct(uniqueConductId) != null
    }

}