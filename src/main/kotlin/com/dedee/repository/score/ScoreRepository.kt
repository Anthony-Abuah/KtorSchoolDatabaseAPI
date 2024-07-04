package com.dedee.repository.score

import com.dedee.model.score.ScoreRequest
import com.dedee.model.score.ScoreResponse
import util.ResponseFeedback


interface ScoreRepository {
    suspend fun getAllScores(): ResponseFeedback<List<ScoreResponse>?>

    suspend fun addScore(score: ScoreRequest): ResponseFeedback<ScoreResponse?>

    suspend fun getScore(uniqueScoreId: String): ResponseFeedback<ScoreResponse?>

    suspend fun deleteScore(uniqueScoreId: String): ResponseFeedback<String>

    suspend fun updateScore(uniqueScoreId: String, updatedScore: ScoreRequest): ResponseFeedback<ScoreResponse?>

    
}



