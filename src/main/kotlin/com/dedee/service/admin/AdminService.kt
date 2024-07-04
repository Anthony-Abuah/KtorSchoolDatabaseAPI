package com.dedee.service.admin

import com.dedee.model.admin.AdministratorRequest
import com.dedee.model.admin.AdministratorResponse


interface AdminService {

    suspend fun getAllAdmins(): List<AdministratorResponse>

    suspend fun registerAdmin(administrator: AdministratorRequest, hashedPassword: String, salt: String): Int

    suspend fun findAdminById(administratorId: Int): AdministratorResponse?

    suspend fun findAdminByEmail(email: String): AdministratorResponse?

    suspend fun findAdminByAdminUserName(adminUsername: String): AdministratorResponse?

    suspend fun deleteAdmin(parameter: String): Int?

    suspend fun updateAdmin(parameter: String, updatedAdmin: AdministratorRequest): AdministratorResponse?

    suspend fun changeAdminPassword(parameter: String, updatedPassword: String, updatedSalt: String): AdministratorResponse?

    suspend fun passwordIsNotValid(password: String, firstName: String, lastName: String): Boolean

}