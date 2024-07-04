package com.dedee.service.admin

import com.dedee.entities.AdministratorEntity
import com.dedee.model.admin.AdministratorRequest
import com.dedee.model.admin.AdministratorResponse
import org.ktorm.database.Database
import org.ktorm.dsl.*
import util.Constants.emptyString
import util.DatabaseEntityFields.AdministratorId
import util.DatabaseEntityFields.Email
import util.Functions.adminUsernameGenerator
import util.Functions.getAdminParameterType

class AdminServiceImpl(
    private val db: Database,
) : AdminService {
    override suspend fun getAllAdmins(): List<AdministratorResponse> {
        val admins = db.from(AdministratorEntity).select()
            .map {
                val administratorId = it[AdministratorEntity.administratorId] ?: -1
                val firstName = it[AdministratorEntity.firstName] ?: emptyString
                val lastName = it[AdministratorEntity.lastName] ?: emptyString
                val adminUsername = it[AdministratorEntity.adminUsername] ?: emptyString
                val password = it[AdministratorEntity.password] ?: emptyString
                val salt = it[AdministratorEntity.salt] ?: emptyString
                val email = it[AdministratorEntity.email] ?: emptyString
                val gender = it[AdministratorEntity.gender] ?: emptyString
                val position = it[AdministratorEntity.position] ?: emptyString
                AdministratorResponse(administratorId, firstName, lastName, adminUsername, password, salt, email, position, gender)
            }
        return admins
    }

    override suspend fun registerAdmin(administrator: AdministratorRequest, hashedPassword: String, salt: String): Int {
        val firstName = administrator.firstName
        val lastName = administrator.lastName
        val adminUsername = adminUsernameGenerator(firstName, lastName)
        return db.insert(AdministratorEntity){
            set(it.firstName, firstName)
            set(it.lastName, lastName)
            set(it.adminUsername, adminUsername)
            set(it.email, administrator.email)
            set(it.password, hashedPassword)
            set(it.salt, salt)
            set(it.gender, administrator.gender)
            set(it.position, administrator.position)
        }
    }

    override suspend fun findAdminById(administratorId: Int): AdministratorResponse? {
        return db.from(AdministratorEntity)
            .select()
            .where{AdministratorEntity.administratorId eq administratorId}
            .map { admin->
                val adminId = admin[AdministratorEntity.administratorId] ?: -1
                val firstName = admin[AdministratorEntity.firstName]
                val lastName = admin[AdministratorEntity.lastName]
                val email = admin[AdministratorEntity.email] ?: emptyString
                val adminUsername = admin[AdministratorEntity.adminUsername] ?: emptyString
                val password = admin[AdministratorEntity.password] ?: emptyString
                val salt = admin[AdministratorEntity.salt] ?: emptyString
                val gender = admin[AdministratorEntity.gender] ?: emptyString
                val position = admin[AdministratorEntity.position] ?: emptyString
                AdministratorResponse(adminId, firstName, lastName, adminUsername, password, salt, email, position, gender)
            }.firstOrNull()
    }

    override suspend fun findAdminByEmail(email: String): AdministratorResponse? {
        return db.from(AdministratorEntity)
            .select()
            .where{AdministratorEntity.email eq email}
            .map { admin->
                val administratorId = admin[AdministratorEntity.administratorId] ?: -1
                val firstName = admin[AdministratorEntity.firstName]
                val lastName = admin[AdministratorEntity.lastName]
                val adminUsername = admin[AdministratorEntity.adminUsername] ?: emptyString
                val password = admin[AdministratorEntity.password] ?: emptyString
                val salt = admin[AdministratorEntity.salt] ?: emptyString
                val gender = admin[AdministratorEntity.gender] ?: emptyString
                val position = admin[AdministratorEntity.position] ?: emptyString
                AdministratorResponse(administratorId, firstName, lastName, adminUsername, password, salt, email, position, gender)
            }.firstOrNull()
    }

    override suspend fun findAdminByAdminUserName(adminUsername: String): AdministratorResponse? {
        return db.from(AdministratorEntity)
            .select()
            .where{AdministratorEntity.adminUsername eq adminUsername}
            .map { admin->
                val administratorId = admin[AdministratorEntity.administratorId] ?: -1
                val firstName = admin[AdministratorEntity.firstName]
                val lastName = admin[AdministratorEntity.lastName]
                val email = admin[AdministratorEntity.email] ?: emptyString
                val password = admin[AdministratorEntity.password] ?: emptyString
                val salt = admin[AdministratorEntity.salt] ?: emptyString
                val gender = admin[AdministratorEntity.gender] ?: emptyString
                val position = admin[AdministratorEntity.position] ?: emptyString
                AdministratorResponse(administratorId, firstName, lastName, adminUsername, password, salt, email, position, gender)
            }.firstOrNull()
    }

    override suspend fun deleteAdmin(parameter: String): Int {
        when(getAdminParameterType(parameter)){
            AdministratorId -> {
                val adminId = try { parameter.toInt() }catch (e: NumberFormatException){ -1 }
                return db.delete(AdministratorEntity) {
                    AdministratorEntity.administratorId eq adminId
                }
            }
            Email -> {
                return db.delete(AdministratorEntity) {
                    AdministratorEntity.email eq parameter
                }
            }
            else -> {
                return db.delete(AdministratorEntity) {
                    AdministratorEntity.adminUsername eq parameter
                }
            }
        }
    }

    override suspend fun updateAdmin(parameter: String, updatedAdmin: AdministratorRequest): AdministratorResponse? {
        when(getAdminParameterType(parameter)){
            AdministratorId -> {
                val adminId = try { parameter.toInt() }catch (e: NumberFormatException){ -1 }
                db.update(AdministratorEntity){
                    set(it.firstName, updatedAdmin.firstName)
                    set(it.lastName, updatedAdmin.lastName)
                    set(it.email, updatedAdmin.email)
                    set(it.gender, updatedAdmin.gender)
                    set(it.position, updatedAdmin.position)
                    set(it.adminUsername, adminUsernameGenerator(updatedAdmin.firstName, updatedAdmin.lastName))
                    where { it.administratorId eq adminId }
                }
                return db.from(AdministratorEntity)
                    .select()
                    .where{AdministratorEntity.administratorId eq adminId}
                    .map { admin->
                        val administratorId = admin[AdministratorEntity.administratorId] ?: -1
                        val firstName = admin[AdministratorEntity.firstName] ?: emptyString
                        val lastName = admin[AdministratorEntity.lastName] ?: emptyString
                        val email = admin[AdministratorEntity.email] ?: emptyString
                        val password = admin[AdministratorEntity.password] ?: emptyString
                        val salt = admin[AdministratorEntity.salt] ?: emptyString
                        val gender = admin[AdministratorEntity.gender] ?: emptyString
                        val position = admin[AdministratorEntity.position] ?: emptyString
                        AdministratorResponse(administratorId, firstName, lastName, adminUsernameGenerator(firstName, lastName), password, salt, email, position, gender)
                    }.firstOrNull()
            }
            Email -> {
                db.update(AdministratorEntity){
                    set(it.firstName, updatedAdmin.firstName)
                    set(it.lastName, updatedAdmin.lastName)
                    set(it.email, updatedAdmin.email)
                    set(it.gender, updatedAdmin.gender)
                    set(it.position, updatedAdmin.position)
                    set(it.adminUsername, adminUsernameGenerator(updatedAdmin.firstName, updatedAdmin.lastName))
                    where { it.email eq parameter }
                }
                return db.from(AdministratorEntity)
                    .select()
                    .where{AdministratorEntity.email eq parameter}
                    .map { admin->
                        val administratorId = admin[AdministratorEntity.administratorId] ?: -1
                        val firstName = admin[AdministratorEntity.firstName] ?: emptyString
                        val lastName = admin[AdministratorEntity.lastName] ?: emptyString
                        val email = admin[AdministratorEntity.email] ?: emptyString
                        val password = admin[AdministratorEntity.password] ?: emptyString
                        val salt = admin[AdministratorEntity.salt] ?: emptyString
                        val gender = admin[AdministratorEntity.gender] ?: emptyString
                        val position = admin[AdministratorEntity.position] ?: emptyString
                        AdministratorResponse(administratorId, firstName, lastName, adminUsernameGenerator(firstName, lastName), password, salt, email, position, gender)
                    }.firstOrNull()
            }
            else -> {
                val updatedAdminUsername = adminUsernameGenerator(updatedAdmin.firstName, updatedAdmin.lastName)
                db.update(AdministratorEntity){
                    set(it.firstName, updatedAdmin.firstName)
                    set(it.lastName, updatedAdmin.lastName)
                    set(it.email, updatedAdmin.email)
                    set(it.gender, updatedAdmin.gender)
                    set(it.position, updatedAdmin.position)
                    set(it.adminUsername, updatedAdminUsername)
                    where { it.adminUsername eq parameter }
                }
                return db.from(AdministratorEntity)
                    .select()
                    .where{AdministratorEntity.adminUsername eq updatedAdminUsername}
                    .map { admin->
                        val administratorId = admin[AdministratorEntity.administratorId] ?: -1
                        val firstName = admin[AdministratorEntity.firstName] ?: emptyString
                        val lastName = admin[AdministratorEntity.lastName] ?: emptyString
                        val email = admin[AdministratorEntity.email] ?: emptyString
                        val password = admin[AdministratorEntity.password] ?: emptyString
                        val salt = admin[AdministratorEntity.salt] ?: emptyString
                        val gender = admin[AdministratorEntity.gender] ?: emptyString
                        val position = admin[AdministratorEntity.position] ?: emptyString
                        AdministratorResponse(administratorId, firstName, lastName, adminUsernameGenerator(firstName, lastName), password, salt, email, position, gender)
                    }.firstOrNull()
            }
        }
    }

    override suspend fun changeAdminPassword(
        parameter: String,
        updatedPassword: String,
        updatedSalt: String
    ): AdministratorResponse? {
        when(getAdminParameterType(parameter)){
            AdministratorId -> {
                val adminId = try { parameter.toInt() }catch (e: NumberFormatException){ -1 }
                db.update(AdministratorEntity){
                    set(it.password, updatedPassword)
                    set(it.salt, updatedSalt)
                    where { it.administratorId eq adminId }
                }
                return db.from(AdministratorEntity)
                    .select()
                    .where{AdministratorEntity.administratorId eq adminId}
                    .map { admin->
                        val administratorId = admin[AdministratorEntity.administratorId] ?: -1
                        val firstName = admin[AdministratorEntity.firstName] ?: emptyString
                        val lastName = admin[AdministratorEntity.lastName] ?: emptyString
                        val email = admin[AdministratorEntity.email] ?: emptyString
                        val password = admin[AdministratorEntity.password] ?: emptyString
                        val salt = admin[AdministratorEntity.salt] ?: emptyString
                        val gender = admin[AdministratorEntity.gender] ?: emptyString
                        val position = admin[AdministratorEntity.position] ?: emptyString
                        AdministratorResponse(administratorId, firstName, lastName, adminUsernameGenerator(firstName, lastName), password, salt, email, position, gender)
                    }.firstOrNull()
            }
            Email -> {
                db.update(AdministratorEntity){
                    set(it.password, updatedPassword)
                    set(it.salt, updatedSalt)
                    where { it.email eq parameter }
                }
                return db.from(AdministratorEntity)
                    .select()
                    .where{AdministratorEntity.email eq parameter}
                    .map { admin->
                        val administratorId = admin[AdministratorEntity.administratorId] ?: -1
                        val firstName = admin[AdministratorEntity.firstName] ?: emptyString
                        val lastName = admin[AdministratorEntity.lastName] ?: emptyString
                        val email = admin[AdministratorEntity.email] ?: emptyString
                        val password = admin[AdministratorEntity.password] ?: emptyString
                        val salt = admin[AdministratorEntity.salt] ?: emptyString
                        val gender = admin[AdministratorEntity.gender] ?: emptyString
                        val position = admin[AdministratorEntity.position] ?: emptyString
                        AdministratorResponse(administratorId, firstName, lastName, adminUsernameGenerator(firstName, lastName), password, salt, email, position, gender)
                    }.firstOrNull()
            }
            else -> {
                db.update(AdministratorEntity){
                    set(it.password, updatedPassword)
                    set(it.salt, updatedSalt)
                    where { it.adminUsername eq parameter }
                }
                return db.from(AdministratorEntity)
                    .select()
                    .where{AdministratorEntity.adminUsername eq parameter}
                    .map { admin->
                        val administratorId = admin[AdministratorEntity.administratorId] ?: -1
                        val firstName = admin[AdministratorEntity.firstName] ?: emptyString
                        val lastName = admin[AdministratorEntity.lastName] ?: emptyString
                        val email = admin[AdministratorEntity.email] ?: emptyString
                        val password = admin[AdministratorEntity.password] ?: emptyString
                        val salt = admin[AdministratorEntity.salt] ?: emptyString
                        val gender = admin[AdministratorEntity.gender] ?: emptyString
                        val position = admin[AdministratorEntity.position] ?: emptyString
                        AdministratorResponse(administratorId, firstName, lastName, adminUsernameGenerator(firstName, lastName), password, salt, email, position, gender)
                    }.firstOrNull()
            }
        }
    }

    override suspend fun passwordIsNotValid(password: String, firstName: String, lastName: String): Boolean {
        val thisFirstName = firstName.lowercase().take(5)
        val thisLastName = lastName.lowercase().take(5)
        val existingPassword = db.from(AdministratorEntity)
            .select()
            .where{AdministratorEntity.password eq password}
            .map { it[AdministratorEntity.password] ?: emptyString
            }.firstOrNull()
        return (password.length < 10 || password.contains(thisFirstName) || password.contains(thisLastName) || (existingPassword != null))

    }


}