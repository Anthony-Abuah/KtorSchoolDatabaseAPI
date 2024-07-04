package com.dedee.repository.admin

import com.dedee.model.admin.AdministratorRequest
import com.dedee.model.admin.AdministratorResponse
import com.dedee.security.hashing.HashService
import com.dedee.service.admin.AdminService
import util.Constants.emptyString
import util.DatabaseEntityFields.AdministratorId
import util.DatabaseEntityFields.Email
import util.Functions.adminUsernameGenerator
import util.Functions.getAdminParameterType
import util.ResponseFeedback

class AdminRepositoryImpl(
    private val adminService: AdminService,
    private val hashService: HashService,
) : AdminRepository {
    override suspend fun getAllAdmins(): ResponseFeedback<List<AdministratorResponse>> {
        val adminResponse = adminService.getAllAdmins()
        return ResponseFeedback(
            data = adminResponse,
            message = "Successfully loaded",
            success = true
        )
    }

    override suspend fun registerAdmin(administrator: AdministratorRequest): ResponseFeedback<AdministratorResponse?> {
        val firstName = administrator.firstName
        val lastName = administrator.lastName
        val adminUsername = adminUsernameGenerator(firstName, lastName)
        val password = administrator.password
        val saltedHash = hashService.generateSaltedHash(password)
        val hashedPassword = saltedHash.hash
        val salt = saltedHash.salt
        val email = administrator.email
        //Check if admin already exists
        //Check if username exists
        return if (adminUserNameExists(adminUsername)){
            ResponseFeedback(
                data = null,
                message = "Admin with name: $firstName $lastName already exists",
                success = false
            )
        }else if (emailExists(email)){
            ResponseFeedback(
                data = null,
                message = "Admin with email: $email exists",
                success = false
            )
        }else if (passwordIsNotValid(password, firstName, lastName)){
            ResponseFeedback(
                data = null,
                message = "Password is weak",
                success = false
            )
        }else{
            adminService.registerAdmin(administrator, hashedPassword, salt)
            val admin = adminService.findAdminByEmail(email)
            ResponseFeedback(
                data = admin,
                message = "Admin successfully added",
                success = true
            )
        }

    }

    override suspend fun getAdminByEmail(email: String): ResponseFeedback<AdministratorResponse> {
        val response = adminService.findAdminByEmail(email)
        return if (response != null){
            ResponseFeedback(
                data = response,
                message = "Admin successfully fetched",
                success = true
            )
        }else{
            ResponseFeedback(
                data = null,
                message = "Admin with $email does not exist",
                success = false
            )
        }
    }

    override suspend fun getAdminByAdminUserName(adminUsername: String): ResponseFeedback<AdministratorResponse> {
        val response = adminService.findAdminByAdminUserName(adminUsername)
        return if (response != null){
            ResponseFeedback(
                data = response,
                message = "Admin successfully fetched",
                success = true
            )
        }else{
            ResponseFeedback(
                data = null,
                message = "Admin with $adminUsername does not exist",
                success = false
            )
        }
    }

    override suspend fun updateAdmin(
        parameter: String,
        updatedAdmin: AdministratorRequest,
    ): ResponseFeedback<AdministratorResponse> {
        val response = adminService.updateAdmin(parameter, updatedAdmin)
        return if (response != null) {
            ResponseFeedback(
                data = response,
                message = "Admin successfully updated",
                success = true
            )
        } else {
            ResponseFeedback(
                data = null,
                message = "Administrator with this credential :$parameter does not exist",
                success = false
            )
        }
    }

    override suspend fun deleteAdmin(parameter: String): ResponseFeedback<String> {
        val response = adminService.deleteAdmin(parameter) ?: -1
        return if (response < 1) {
            ResponseFeedback(
                data = "Could not delete admin",
                message = "Unknown error",
                success = false
            )
        } else {
            ResponseFeedback(
                data = "Admin successfully deleted",
                message = "Admin successfully deleted",
                success = true
            )
        }
    }

    override suspend fun changePassword(
        parameter: String,
        currentPassword: String,
        updatedPassword: String,
    ): ResponseFeedback<AdministratorResponse> {
        when(getAdminParameterType(parameter)){
            AdministratorId -> {
                val adminId = try { parameter.toInt() }catch (e: NumberFormatException){-1}
                val admin = adminService.findAdminById(adminId)
                val firstName = admin?.firstName ?: emptyString
                val lastName = admin?.lastName ?: emptyString
                val password = admin?.password ?: emptyString
                val salt = admin?.salt ?: emptyString

                val saltedHash = hashService.generateSaltedHash(updatedPassword)
                val updatedHashedPassword = saltedHash.hash
                val updatedSalt = saltedHash.salt
                val passwordDoesNotMatch = !hashService.verify(currentPassword, salt, password)

                val passwordIsNotValid = passwordIsNotValid(updatedPassword, firstName, lastName)
                return if (passwordDoesNotMatch){
                    ResponseFeedback(
                        data = null,
                        message = "Passwords do no match",
                        success = false
                    )
                }else if (passwordIsNotValid){
                    ResponseFeedback(
                        data = null,
                        message = "Password is weak or already in use",
                        success = false
                    )
                }else{
                    val response = adminService.changeAdminPassword(parameter, updatedHashedPassword, updatedSalt)
                    ResponseFeedback(
                        data = response,
                        message = "Password successfully changed",
                        success = true
                    )
                }
            }
            Email -> {
                val admin = adminService.findAdminByEmail(parameter)
                val firstName = admin?.firstName ?: emptyString
                val lastName = admin?.lastName ?: emptyString
                val password = admin?.password ?: emptyString
                val salt = admin?.salt ?: emptyString

                val saltedHash = hashService.generateSaltedHash(updatedPassword)
                val updatedHashedPassword = saltedHash.hash
                val updatedSalt = saltedHash.salt
                val passwordDoesNotMatch = !hashService.verify(currentPassword, salt, password)

                val passwordIsNotValid = passwordIsNotValid(updatedPassword, firstName, lastName)
                return if (passwordDoesNotMatch){
                    ResponseFeedback(
                        data = null,
                        message = "Passwords do no match",
                        success = false
                    )
                }else if (passwordIsNotValid){
                    ResponseFeedback(
                        data = null,
                        message = "Password is weak or already in use",
                        success = false
                    )
                }else{
                    val response = adminService.changeAdminPassword(parameter, updatedHashedPassword, updatedSalt)
                    ResponseFeedback(
                        data = response,
                        message = "Password successfully changed",
                        success = true
                    )
                }
            }
            else -> {
                val admin = adminService.findAdminByAdminUserName(parameter)
                val firstName = admin?.firstName ?: emptyString
                val lastName = admin?.lastName ?: emptyString
                val password = admin?.password ?: emptyString
                val salt = admin?.salt ?: emptyString

                val saltedHash = hashService.generateSaltedHash(updatedPassword)
                val updatedHashedPassword = saltedHash.hash
                val updatedSalt = saltedHash.salt
                val passwordDoesNotMatch = !hashService.verify(currentPassword, salt, password)

                val passwordIsNotValid = passwordIsNotValid(updatedPassword, firstName, lastName)
                return if (passwordDoesNotMatch){
                    ResponseFeedback(
                        data = null,
                        message = "Passwords do no match",
                        success = false
                    )
                }else if (passwordIsNotValid){
                    ResponseFeedback(
                        data = null,
                        message = "Password is weak or already in use",
                        success = false
                    )
                }else{
                    val response = adminService.changeAdminPassword(parameter, updatedHashedPassword, updatedSalt)
                    ResponseFeedback(
                        data = response,
                        message = "Password successfully changed",
                        success = true
                    )
                }
            }
        }
    }


    private suspend fun emailExists(email: String): Boolean {
        return adminService.findAdminByEmail(email) != null
    }
    private suspend fun adminUserNameExists(email: String): Boolean {
        return adminService.findAdminByAdminUserName(email) != null
    }
    private suspend fun passwordIsNotValid(password: String, firstName: String, lastName: String): Boolean {
        return adminService.passwordIsNotValid(password, firstName, lastName)
    }
}