package com.dedee.route

import com.dedee.model.score.ScoreRequest
import com.dedee.repository.score.ScoreRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import util.Constants.emptyString
import util.DatabaseEntityFields.UniqueScoreId
import util.Routes.deleteScore
import util.Routes.getAllScores
import util.Routes.getScore
import util.Routes.registerScore
import util.Routes.updateScore

fun Application.scoreRoutes(scoreRepository: ScoreRepository) {
    routing {
        get(getAllScores) {
            val result = scoreRepository.getAllScores()
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }
        get(getScore) {
            val uniqueScoreId = call.parameters[UniqueScoreId] ?: emptyString
            val result = scoreRepository.getScore(uniqueScoreId)
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }

        post (registerScore){
            val scoreRequest = call.receive<ScoreRequest>()
            val result = scoreRepository.addScore(scoreRequest)
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }

        put(updateScore){
            val uniqueScoreId = call.parameters[UniqueScoreId] ?: emptyString
            val scoreRequest = call.receive<ScoreRequest>()
            val result = scoreRepository.updateScore(uniqueScoreId, scoreRequest)
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }


        delete (deleteScore){
            val uniqueScoreId = call.parameters[UniqueScoreId] ?: emptyString
            val result = scoreRepository.deleteScore(uniqueScoreId)
            if (result.success) {
                call.respond(HttpStatusCode.OK, result)
            }else{
                call.respond(HttpStatusCode.BadRequest, result)
            }
        }
    }
}
