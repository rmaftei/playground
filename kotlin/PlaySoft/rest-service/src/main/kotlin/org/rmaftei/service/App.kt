package org.rmaftei.service

import com.google.gson.GsonBuilder
import org.rmaftei.service.json.GameSerializer
import org.rmaftei.service.model.game.Game
import org.rmaftei.service.services.game.SimpleGameService
import spark.Spark.*

val serviceVersion = "/v1"

fun main(args: Array<String>) {

    val gameService = SimpleGameService()

    val gson = GsonBuilder().registerTypeAdapter(Game::class.java, GameSerializer()).create()

    get(serviceVersion + "/game") { req, res ->
        gson.toJson(gameService.getAllGames())
    }

}