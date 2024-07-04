package com.dedee.service.parent

import com.dedee.entities.ParentEntity
import com.dedee.model.parent.ParentRequest
import com.dedee.model.parent.ParentResponse
import org.ktorm.database.Database
import org.ktorm.dsl.*
import util.Constants.emptyString

class ParentServiceImpl(
    private val db: Database
) : ParentService {
    override suspend fun getAllParents(): List<ParentResponse>? {
        val parents = db.from(ParentEntity).select()
            .map {
                val firstName = it[ParentEntity.firstName] ?: emptyString
                val lastName = it[ParentEntity.lastName] ?: emptyString
                val parentUsername = it[ParentEntity.parentUsername] ?: emptyString
                val password = it[ParentEntity.password] ?: emptyString
                val salt = it[ParentEntity.salt] ?: emptyString
                val email = it[ParentEntity.email] ?: emptyString
                val gender = it[ParentEntity.gender] ?: emptyString
                val contact = it[ParentEntity.contact] ?: emptyString
                val wards = it[ParentEntity.wards] ?: emptyString
                val hasAnEnrolledWard = it[ParentEntity.hasAnEnrolledWard]
                ParentResponse(firstName, lastName, parentUsername, password, salt, email, contact, gender, hasAnEnrolledWard, wards)
            }
        return parents.ifEmpty { null }
    }

    override suspend fun registerParent(parent: ParentRequest, parentUsername: String, hashedPassword: String, salt: String): Int {
        return db.insert(ParentEntity){
            set(it.firstName, parent.firstName)
            set(it.lastName, parent.lastName)
            set(it.parentUsername, parentUsername)
            set(it.email, parent.email)
            set(it.password, hashedPassword)
            set(it.salt, salt)
            set(it.gender, parent.gender)
            set(it.contact, parent.contact)
        }
    }


    override suspend fun findParentByEmail(email: String): ParentResponse? {
        return db.from(ParentEntity)
            .select()
            .where{ParentEntity.email eq email}
            .map {
                val firstName = it[ParentEntity.firstName] ?: emptyString
                val lastName = it[ParentEntity.lastName] ?: emptyString
                val parentUsername = it[ParentEntity.parentUsername] ?: emptyString
                val password = it[ParentEntity.password] ?: emptyString
                val salt = it[ParentEntity.salt] ?: emptyString
                val gender = it[ParentEntity.gender] ?: emptyString
                val contact = it[ParentEntity.contact] ?: emptyString
                val wards = it[ParentEntity.wards] ?: emptyString
                val hasAnEnrolledWard = it[ParentEntity.hasAnEnrolledWard]
                ParentResponse(firstName, lastName, parentUsername, password, salt, email, contact, gender, hasAnEnrolledWard, wards)
            }.firstOrNull()
    }

    override suspend fun findParentByParentUserName(parentUsername: String): ParentResponse? {
        return db.from(ParentEntity)
            .select()
            .where{ParentEntity.parentUsername eq parentUsername}
            .map {
                val firstName = it[ParentEntity.firstName] ?: emptyString
                val lastName = it[ParentEntity.lastName] ?: emptyString
                val password = it[ParentEntity.password] ?: emptyString
                val salt = it[ParentEntity.salt] ?: emptyString
                val email = it[ParentEntity.email] ?: emptyString
                val gender = it[ParentEntity.gender] ?: emptyString
                val contact = it[ParentEntity.contact] ?: emptyString
                val wards = it[ParentEntity.wards] ?: emptyString
                val hasAnEnrolledWard = it[ParentEntity.hasAnEnrolledWard]
                ParentResponse(firstName, lastName, parentUsername, password, salt, email, contact, gender, hasAnEnrolledWard, wards)
            }.firstOrNull()
    }

    override suspend fun updateParent(parentUsername: String, updatedParent: ParentRequest): ParentResponse? {
        val updateResponse = db.update(ParentEntity){
            val firstName = updatedParent.firstName ?: emptyString
            val lastName = updatedParent.lastName ?: emptyString
            val contact = updatedParent.contact
            set(it.firstName, firstName)
            set(it.lastName, lastName)
            set(it.email, updatedParent.email)
            set(it.gender, updatedParent.gender)
            set(it.contact, contact)
            where { it.parentUsername eq parentUsername }
        }
        return if (updateResponse < 1){
            null
        }else{
            db.from(ParentEntity)
                .select()
                .where{ ParentEntity.parentUsername eq parentUsername}
                .map {
                    val firstName = it[ParentEntity.firstName] ?: emptyString
                    val lastName = it[ParentEntity.lastName] ?: emptyString
                    val password = it[ParentEntity.password] ?: emptyString
                    val salt = it[ParentEntity.salt] ?: emptyString
                    val email = it[ParentEntity.email] ?: emptyString
                    val gender = it[ParentEntity.gender] ?: emptyString
                    val contact = it[ParentEntity.contact] ?: emptyString
                    val wards = it[ParentEntity.wards] ?: emptyString
                    val hasAnEnrolledWard = it[ParentEntity.hasAnEnrolledWard]
                    ParentResponse(firstName, lastName, parentUsername, password, salt, email, contact, gender, hasAnEnrolledWard, wards)
                }.firstOrNull()
        }
    }

    override suspend fun addWard(wards: String, parentUsername: String) {
        db.update(ParentEntity){
            set(it.wards, wards)
            where { it.parentUsername eq parentUsername }
        }
    }


    override suspend fun deleteParent(parentUsername: String): Int {
        return db.delete(ParentEntity) {
            ParentEntity.parentUsername eq parentUsername
        }
    }

    override suspend fun changeParentPassword(
        parentUsername: String,
        updatedPassword: String,
        updatedSalt: String,
    ): ParentResponse? {
        val updateResponse = db.update(ParentEntity){
            set(it.password, updatedPassword)
            set(it.salt, updatedSalt)
            where { it.parentUsername eq parentUsername }
        }
        return if (updateResponse < 1){
            null
        }else{
            db.from(ParentEntity)
                .select()
                .where{ ParentEntity.parentUsername eq parentUsername}
                .map {
                    val firstName = it[ParentEntity.firstName] ?: emptyString
                    val lastName = it[ParentEntity.lastName] ?: emptyString
                    val password = it[ParentEntity.password] ?: emptyString
                    val salt = it[ParentEntity.salt] ?: emptyString
                    val email = it[ParentEntity.email] ?: emptyString
                    val gender = it[ParentEntity.gender] ?: emptyString
                    val contact = it[ParentEntity.contact] ?: emptyString
                    val wards = it[ParentEntity.wards] ?: emptyString
                    val hasAnEnrolledWard = it[ParentEntity.hasAnEnrolledWard]
                    ParentResponse(firstName, lastName, parentUsername, password, salt, email, contact, gender, hasAnEnrolledWard, wards)
                }.firstOrNull()
        }

    }

    override suspend fun passwordIsNotValid(password: String, firstName: String, lastName: String): Boolean {
        val thisFirstName = firstName.lowercase().take(5)
        val thisLastName = lastName.lowercase().take(5)
        val existingPassword = db.from(ParentEntity)
            .select()
            .where{ParentEntity.password eq password}
            .map { it[ParentEntity.password] ?: emptyString
            }.firstOrNull()
        return (password.length < 10 || password.contains(thisFirstName) || password.contains(thisLastName) || (existingPassword != null))
    }

}