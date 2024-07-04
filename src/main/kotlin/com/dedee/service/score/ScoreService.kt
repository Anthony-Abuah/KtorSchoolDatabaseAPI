package com.dedee.service.score

import com.dedee.model.score.ScoreRequest
import com.dedee.model.score.ScoreResponse

interface ScoreService {

    suspend fun getAllScores(): List<ScoreResponse>?

    suspend fun addScore(score: ScoreRequest, uniqueScoreId: String): Int

    suspend fun getScore(uniqueScoreId: String): ScoreResponse?

    suspend fun updateScore(uniqueScoreId: String, updatedScore: ScoreRequest): ScoreResponse?

    suspend fun deleteScore(uniqueScoreId: String): Int?

}