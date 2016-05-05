package org.rmaftei

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.rmaftei.json.GameSerializer
import org.rmaftei.model.game.Game
import org.rmaftei.services.game.SimpleGameService
import spark.Spark.*

val serviceVersion = "/v1"

fun main(args: Array<String>) {

    val gameService = SimpleGameService()

    val gson = GsonBuilder().registerTypeAdapter(Game::class.java, GameSerializer()).create()

    get(serviceVersion + "/game") { req, res ->
        gson.toJson(gameService.getAllGames())
    }

}