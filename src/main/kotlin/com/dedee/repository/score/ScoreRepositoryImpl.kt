package com.dedee.repository.score

import com.dedee.model.score.ScoreInfo
import com.dedee.model.score.ScoreRequest
import com.dedee.model.score.ScoreResponse
import com.dedee.service.grading.GradingService
import com.dedee.service.score.ScoreService
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import util.Constants.emptyString
import util.Functions.uniqueScoreIdGenerator
import util.ResponseFeedback

class ScoreRepositoryImpl(
    private val scoreService: ScoreService,
    private val gradingService: GradingService,
    private val gson: Gson,
) : ScoreRepository{
    override suspend fun getAllScores(): ResponseFeedback<List<ScoreResponse>?> {
        val scoreResponse = scoreService.getAllScores()
        return if (scoreResponse.isNullOrEmpty()) {
            ResponseFeedback(
                data = null,
                message = "Could not fetch all scores",
                success = false
            )
        }else{
            ResponseFeedback(
                data = scoreResponse,
                message = "All scores successfully loaded",
                success = true
            )
        }
    }

    override suspend fun addScore(score: ScoreRequest): ResponseFeedback<ScoreResponse?> {
        val uniqueStudentId = score.uniqueStudentId ?: emptyString
        val uniqueSubjectId = score.uniqueSubjectId ?: emptyString
        val uniqueTermName = score.uniqueTermName ?: emptyString
        val uniqueScoreId = uniqueScoreIdGenerator(uniqueStudentId, uniqueSubjectId, uniqueTermName)
        return if (uniqueScoreIdExists(uniqueScoreId)){
            ResponseFeedback(
                data = null,
                message = "Cannot add score because it already exists",
                success = false
            )
        }else{
            val scoreResponse = scoreService.addScore(score, uniqueScoreId)
            if (scoreResponse < 1 ) {
                ResponseFeedback(
                    data = null,
                    message = "Unable to register score",
                    success = false
                )
            }else{
                val thisScore = scoreService.getScore(uniqueScoreId)
                val uniqueGradingId = score.uniqueGradingId
                val grading = uniqueGradingId?.let { gradingService.getGrading(it) }
                val scoresJSON = grading?.scores
                val listOfScoreInfoType = object : TypeToken<List<ScoreInfo>>() {}.type
                val scoreInfoList: List<ScoreInfo> = gson.fromJson(scoresJSON, listOfScoreInfoType)
                val mutableListOfScoreInfo = scoreInfoList.toMutableList()
                thisScore?.toScoreInfo()?.let { mutableListOfScoreInfo.add(it) }
                val updatedScoreListJSON = gson.toJson(mutableListOfScoreInfo)
                gradingService.addScore(updatedScoreListJSON, uniqueGradingId ?: emptyString)
                ResponseFeedback(
                    data = thisScore,
                    message = "Score successfully registered",
                    success = true
                )
            }
        }
    }

    override suspend fun getScore(
        uniqueScoreId: String,
    ): ResponseFeedback<ScoreResponse?> {
        val response = scoreService.getScore(uniqueScoreId)
        return if (response == null){
            ResponseFeedback(
                data = null,
                message = "No score matches the given criteria",
                success = false
            )
        }else{
            ResponseFeedback(
                data = response,
                message = "Scores fetched successfully",
                success = true
            )
        }
    }

    override suspend fun deleteScore(uniqueScoreId: String): ResponseFeedback<String> {
        val response =  scoreService.deleteScore(uniqueScoreId) ?: -1
        return if (response < 1){
            ResponseFeedback(
                data = "Unable to delete score",
                message = "Unknown Error",
                success = false
            )
        }else{
            ResponseFeedback(
                data = "Score deleted successfully",
                message = "Score deleted successfully",
                success = true
            )
        }

    }

    override suspend fun updateScore(
        uniqueScoreId: String,
        updatedScore: ScoreRequest,
    ): ResponseFeedback<ScoreResponse?> {
        val thisScore = scoreService.updateScore(uniqueScoreId, updatedScore)
        return if (thisScore == null){
            ResponseFeedback(
                data = null,
                message = "Unable to update score",
                success = false
            )
        }else{
            val uniqueGradingId = updatedScore.uniqueGradingId
            val grading = uniqueGradingId?.let { gradingService.getGrading(it) }
            val scoresJSON = grading?.scores
            val listOfScoreInfoType = object : TypeToken<List<ScoreInfo>>() {}.type
            val scoreInfoList: List<ScoreInfo> = gson.fromJson(scoresJSON, listOfScoreInfoType)
            val mutableListOfScoreInfo = scoreInfoList.toMutableList()
            mutableListOfScoreInfo.removeIf { it.uniqueScoreId == uniqueScoreId }
            thisScore.toScoreInfo().let { mutableListOfScoreInfo.add(it) }
            val updatedScoreListJSON = gson.toJson(mutableListOfScoreInfo)
            gradingService.addScore(updatedScoreListJSON, uniqueGradingId ?: emptyString)
            ResponseFeedback(
                data = thisScore,
                message = "Score updated successfully",
                success = true
            )
        }
    }


    private suspend fun uniqueScoreIdExists(uniqueScoreId: String): Boolean {
        return scoreService.getScore(uniqueScoreId) != null
    }

}