package com.dedee.service.parent

import com.dedee.model.parent.ParentRequest
import com.dedee.model.parent.ParentResponse


interface ParentService {

    suspend fun getAllParents(): List<ParentResponse>?

    suspend fun registerParent(parent: ParentRequest, parentUsername: String, hashedPassword: String, salt: String): Int

    suspend fun findParentByEmail(email: String): ParentResponse?

    suspend fun findParentByParentUserName(parentUsername: String): ParentResponse?

    suspend fun updateParent(parentUsername: String, updatedParent: ParentRequest): ParentResponse?

    suspend fun addWard(wards: String, parentUsername: String)

    suspend fun deleteParent(parentUsername: String): Int?

    suspend fun changeParentPassword(parentUsername: String, updatedPassword: String, updatedSalt: String): ParentResponse?

    suspend fun passwordIsNotValid(password: String, firstName: String, lastName: String): Boolean
}