package org.rmaftei.service

import com.google.gson.GsonBuilder
import org.rmaftei.businesslogic.game.GameApplication
import org.rmaftei.businesslogic.game.domain.Game as BLGame
import org.rmaftei.service.json.GameDeserializer
import org.rmaftei.service.json.GameSerializer
import org.rmaftei.service.model.game.Game
import org.rmaftei.service.repositories.ListGameRepository
import org.rmaftei.service.routes.game.GameServiceRoutes
import spark.Spark.*

val SERVICE_VERSION = "/v1"
val GAME_PATH = "/games"

val PATH = SERVICE_VERSION.plus(GAME_PATH)

var gameRepository  = ListGameRepository()

fun main(args: Array<String>) {

    val gameApplication = GameApplication(gameRepository)

    val gson = GsonBuilder()
            .registerTypeAdapter(Game::class.java, GameSerializer())
            .registerTypeAdapter(Game::class.java, GameDeserializer())
            .create()

    get(PATH, GameServiceRoutes.GetAll(gameApplication))

    post(PATH, { req, res ->
        val bodyAsString = req.body()

        val game  = gson.fromJson(bodyAsString, Game::class.java)

        gameApplication.createGame(BLGame(game.id, game.startTime, game.location, game.description, game.createdBy))

        res.type("text/json")
        gson.toJson(gameApplication.getAllGames())
    })

    put(PATH + "/:id") { req, res ->
        val gameId = req.params("id")

        val bodyAsString = req.body()

        val game  = gson.fromJson(bodyAsString, Game::class.java)

        val gametoUpdate = game.copy(id = gameId)

        gameApplication.updateGame(
                BLGame(gametoUpdate.id, gametoUpdate.startTime, gametoUpdate.location,
                        gametoUpdate.description, gametoUpdate.createdBy))

        res.type("text/json")
        gson.toJson(gameApplication.getAllGames())
    }

    delete(PATH + "/:id") { req, res ->
        val idGame = req.params("id")

        gameApplication.deleteGame(idGame)

        res.type("text/json")
        gson.toJson(gameApplication.getAllGames())
    }

}