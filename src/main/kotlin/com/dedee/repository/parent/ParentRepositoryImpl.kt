package com.dedee.repository.parent

import com.dedee.model.parent.ParentRequest
import com.dedee.model.parent.ParentResponse
import com.dedee.security.hashing.HashService
import com.dedee.service.parent.ParentService
import util.Constants.emptyString
import util.Functions.parentUsernameGenerator
import util.ResponseFeedback

class ParentRepositoryImpl(
    private val parentService: ParentService,
    private val hashService: HashService,
) : ParentRepository{

    override suspend fun getAllParents(): ResponseFeedback<List<ParentResponse>> {
        val parentResponse = parentService.getAllParents()
        return ResponseFeedback(
            data = parentResponse,
            message = "Successfully loaded",
            success = true
        )
    }

    override suspend fun registerParent(parent: ParentRequest): ResponseFeedback<ParentResponse?> {
        val firstName = parent.firstName ?: emptyString
        val lastName = parent.lastName ?: emptyString
        val parentUsername = parentUsernameGenerator(firstName, lastName)
        val password = parent.password
        val saltedHash = hashService.generateSaltedHash(password)
        val hashedPassword = saltedHash.hash
        val salt = saltedHash.salt
        val email = parent.email
        //Check if parent already exists
        //Check if username exists
        return if (parentUserNameExists(parentUsername)){
            ResponseFeedback(
                data = null,
                message = "Parent with name: $firstName $lastName already exists",
                success = false
            )
        }else if (emailExists(email)){
            ResponseFeedback(
                data = null,
                message = "Parent with email: $email exists",
                success = false
            )
        }else if (passwordIsNotValid(password, firstName, lastName)){
            ResponseFeedback(
                data = null,
                message = "Password is weak",
                success = false
            )
        }else{


            val registerParentResponse = parentService.registerParent(parent, parentUsername, hashedPassword, salt)
            if (registerParentResponse < 1){
                ResponseFeedback(
                    data = null,
                    message = "Unable to register parent",
                    success = false
                )
            }else {
                val registeredParent = parentService.findParentByParentUserName(parentUsername)
                ResponseFeedback(
                    data = registeredParent,
                    message = "Parent successfully added",
                    success = true
                )
            }
        }
    }

    override suspend fun getParentByEmail(email: String): ResponseFeedback<ParentResponse> {
        val response = parentService.findParentByEmail(email)
        return if (response != null){
            ResponseFeedback(
                data = response,
                message = "Parent successfully fetched",
                success = true
            )
        }else{
            ResponseFeedback(
                data = null,
                message = "Parent with $email does not exist",
                success = true
            )
        }
    }

    override suspend fun getParentByUsername(parentUsername: String): ResponseFeedback<ParentResponse> {
        val response = parentService.findParentByParentUserName(parentUsername)
        return if (response != null){
            ResponseFeedback(
                data = response,
                message = "Parent successfully fetched",
                success = true
            )
        }else{
            ResponseFeedback(
                data = null,
                message = "Parent with $parentUsername does not exist",
                success = false
            )
        }
    }

    override suspend fun deleteParent(parentUsername: String): ResponseFeedback<String> {
        val response = parentService.deleteParent(parentUsername) ?: -1
        return if (response < 1) {
            ResponseFeedback(
                data = "Could not delete parent",
                message = "Unknown error",
                success = false
            )
        } else {
            ResponseFeedback(
                data = "Parent successfully deleted",
                message = "Parent successfully deleted",
                success = true
            )
        }
    }

    override suspend fun updateParent(
        parentUsername: String,
        updatedParent: ParentRequest,
    ): ResponseFeedback<ParentResponse> {
        val response = parentService.updateParent(parentUsername, updatedParent)
        return if (response != null) {
            ResponseFeedback(
                data = response,
                message = "Parent successfully updated",
                success = true
            )
        } else {
            ResponseFeedback(
                data = null,
                message = "Parent with this credential :$parentUsername does not exist",
                success = false
            )
        }
    }

    override suspend fun changeParentPassword(
        parentUsername: String,
        currentPassword: String,
        updatedPassword: String,
    ): ResponseFeedback<ParentResponse> {
        val parent = parentService.findParentByParentUserName(parentUsername)
        val firstName = parent?.firstName ?: emptyString
        val lastName = parent?.lastName ?: emptyString
        val password = parent?.password ?: emptyString

        val salt = parent?.salt ?: emptyString
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
            val response = parentService.changeParentPassword(parentUsername, updatedHashedPassword, updatedSalt)
            ResponseFeedback(
                data = response,
                message = "Password successfully changed",
                success = true
            )
        }
    }

    private suspend fun emailExists(email: String): Boolean {
        return parentService.findParentByEmail(email) != null
    }
    private suspend fun parentUserNameExists(parentUsername: String): Boolean {
        return parentService.findParentByParentUserName(parentUsername) != null
    }
    private suspend fun passwordIsNotValid(password: String, firstName: String, lastName: String): Boolean {
        return parentService.passwordIsNotValid(password, firstName, lastName)
    }
}