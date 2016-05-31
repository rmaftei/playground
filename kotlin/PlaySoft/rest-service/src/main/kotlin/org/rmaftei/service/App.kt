package org.rmaftei.service

import com.google.gson.GsonBuilder
import org.rmaftei.adapters.SimpleMongoDBAdapter
import org.rmaftei.businesslogic.game.GameApplication
import org.rmaftei.businesslogic.game.port.GamesPort
import org.rmaftei.service.json.GameDeserializer
import org.rmaftei.service.json.GameSerializer
import org.rmaftei.service.model.game.Game
import org.rmaftei.service.routes.game.GameServiceRoutes
import spark.Spark.*
import org.rmaftei.businesslogic.game.domain.Game as BLGame

val SERVICE_VERSION = "/v1"
val GAME_PATH = "/games"

val PATH = SERVICE_VERSION.plus(GAME_PATH)

var gamesPort: GamesPort = SimpleMongoDBAdapter()

val JSON_ENGINE = GsonBuilder()
        .registerTypeAdapter(Game::class.java, GameSerializer())
        .registerTypeAdapter(Game::class.java, GameDeserializer())
        .create()

fun main(args: Array<String>) {

    val gameApplication = GameApplication(gamesPort)

    val currentUser = "currentUser"

    get(PATH, GameServiceRoutes.GetAll(gameApplication))

    post(PATH, { req, res ->
        val bodyAsString = req.body()

        val game  = JSON_ENGINE.fromJson(bodyAsString, Game::class.java)

        val newBLGame =
                gameApplication
                        .createGame(BLGame(game.id, game.startTime, game.location, game.description, currentUser))

        res.type("text/json")

        JSON_ENGINE.toJson(Game(newBLGame.id, newBLGame.startGame, newBLGame.location, newBLGame.description, newBLGame.createdBy))
    })

    put(PATH + "/:id") { req, res ->
        val gameId = req.params("id")

        val bodyAsString = req.body()

        val game  = JSON_ENGINE.fromJson(bodyAsString, Game::class.java)

        val gametoUpdate = game.copy(id = gameId)

        gameApplication.updateGame(
                BLGame(gametoUpdate.id, gametoUpdate.startTime, gametoUpdate.location,
                        gametoUpdate.description, currentUser))

        res.type("text/json")
        JSON_ENGINE.toJson(gameApplication.getAllGames())
    }

    delete(PATH + "/:id") { req, res ->
        val idGame = req.params("id")

        gameApplication.deleteGame(idGame)

        res.type("text/json")
        JSON_ENGINE.toJson(gameApplication.getAllGames())
    }

}