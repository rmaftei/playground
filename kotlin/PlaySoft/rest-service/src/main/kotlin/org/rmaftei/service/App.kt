package org.rmaftei.service

import com.google.gson.GsonBuilder
import org.rmaftei.service.json.GameDeserializer
import org.rmaftei.service.json.GameSerializer
import org.rmaftei.service.model.game.Game
import org.rmaftei.service.repositories.ListGameRepository
import org.rmaftei.service.services.game.SimpleGameService
import spark.Spark.*

val SERVICE_VERSION = "/v1"
val GAME_PATH = "/games"

val PATH = SERVICE_VERSION.plus(GAME_PATH)

fun main(args: Array<String>) {

    val gameService = SimpleGameService(ListGameRepository())

    val gson = GsonBuilder()
            .registerTypeAdapter(Game::class.java, GameSerializer())
            .registerTypeAdapter(Game::class.java, GameDeserializer())
            .create()

    get(PATH) { req, res ->
        res.type("text/json")
        gson.toJson(gameService.getAllGames())
    }

    post(PATH, { req, res ->
        val bodyAsString = req.body()

        val game  = gson.fromJson(bodyAsString, Game::class.java)

        gameService.createGame(game)

        res.type("text/json")
        gson.toJson(gameService.getAllGames())
    })

    put(PATH) { req, res ->
        val bodyAsString = req.body()

        val game  = gson.fromJson(bodyAsString, Game::class.java)

        gameService.updateGame(game)

        res.type("text/json")
        gson.toJson(gameService.getAllGames())
    }

    delete(PATH) { req, res ->
        val idGame = req.params("id")

        gameService.deleteGame(idGame)

        res.type("text/json")
        gson.toJson(gameService.getAllGames())
    }

}