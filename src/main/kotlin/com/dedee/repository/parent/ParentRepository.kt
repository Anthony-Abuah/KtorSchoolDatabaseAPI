package com.dedee.repository.parent

import com.dedee.model.parent.ParentRequest
import com.dedee.model.parent.ParentResponse
import util.ResponseFeedback

interface ParentRepository {
    suspend fun getAllParents(): ResponseFeedback<List<ParentResponse>>

    suspend fun registerParent(parent: ParentRequest): ResponseFeedback<ParentResponse?>

    suspend fun getParentByEmail(email: String): ResponseFeedback<ParentResponse>

    suspend fun getParentByUsername(parentUsername: String): ResponseFeedback<ParentResponse>

    suspend fun deleteParent(parentUsername: String): ResponseFeedback<String>

    suspend fun updateParent(parentUsername: String, updatedParent: ParentRequest): ResponseFeedback<ParentResponse>

    suspend fun changeParentPassword(parentUsername: String, currentPassword: String, updatedPassword: String): ResponseFeedback<ParentResponse>
    
}



