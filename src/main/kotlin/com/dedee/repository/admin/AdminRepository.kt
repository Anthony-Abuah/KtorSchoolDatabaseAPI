package com.dedee.repository.admin

import com.dedee.model.admin.AdministratorRequest
import com.dedee.model.admin.AdministratorResponse
import util.ResponseFeedback


interface AdminRepository {

    suspend fun getAllAdmins(): ResponseFeedback<List<AdministratorResponse>>

    suspend fun registerAdmin(administrator: AdministratorRequest): ResponseFeedback<AdministratorResponse?>

    suspend fun getAdminByEmail(email: String): ResponseFeedback<AdministratorResponse>

    suspend fun getAdminByAdminUserName(adminUsername: String): ResponseFeedback<AdministratorResponse>

    suspend fun updateAdmin(parameter: String, updatedAdmin: AdministratorRequest): ResponseFeedback<AdministratorResponse>

    suspend fun deleteAdmin(parameter: String): ResponseFeedback<String>

    suspend fun changePassword(parameter: String, currentPassword: String, updatedPassword: String): ResponseFeedback<AdministratorResponse>

}