package org.rmaftei.service

import com.google.gson.GsonBuilder
import org.rmaftei.service.json.GameSerializer
import org.rmaftei.service.model.game.Game
import org.rmaftei.service.services.game.SimpleGameService
import spark.Spark.*

val SERVICE_VERSION = "/v1"
val GAME_PATH = "/games"

val PATH = SERVICE_VERSION.plus(GAME_PATH)

fun main(args: Array<String>) {

    val gameService = SimpleGameService()

    val gson = GsonBuilder().registerTypeAdapter(Game::class.java, GameSerializer()).create()

    get(PATH) { req, res ->
        gson.toJson(gameService.getAllGames())
    }

    post(PATH) { req, res ->
        throw UnsupportedOperationException()
    }

    put(PATH) { req, res ->
        throw UnsupportedOperationException()
    }

    delete(PATH) { req, res ->
        throw UnsupportedOperationException()
    }

}