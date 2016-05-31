package org.rmaftei.service

import com.google.gson.GsonBuilder
import org.rmaftei.businesslogic.game.GameApplication
import org.rmaftei.businesslogic.game.port.GamesPort
import org.rmaftei.service.adapter.SimpleMongoDBAdapter
import org.rmaftei.service.json.CredentialDeserializer
import org.rmaftei.service.json.GameDeserializer
import org.rmaftei.service.json.GameSerializer
import org.rmaftei.service.model.game.Game
import org.rmaftei.service.model.user.Credentials
import org.rmaftei.service.routes.game.GameServiceRoutes
import spark.Spark.*
import java.util.*
import org.rmaftei.businesslogic.game.domain.Game as BLGame

val SERVICE_VERSION = "/v1"
val GAME_PATH = "/games"

val PATH = SERVICE_VERSION.plus(GAME_PATH)

var gamesPort: GamesPort = SimpleMongoDBAdapter()

val JSON_ENGINE = GsonBuilder()
        .registerTypeAdapter(Game::class.java, GameSerializer())
        .registerTypeAdapter(Game::class.java, GameDeserializer())
        .registerTypeAdapter(Credentials::class.java, CredentialDeserializer())
        .create()

fun main(args: Array<String>) {

    val gameApplication = GameApplication(gamesPort)

    val currentUser = "currentUser"

    val users = mapOf(
            "user1" to "7c6a180b36896a0a8c02787eeafb0e4c",
            "user2" to "6cb75f652a9b52798eb6cf2201057c73")

    val tokens = mutableMapOf("" to "")

    before { req, resp ->
        if(!req.pathInfo().equals("/v1/auth")) {

            if(null == req.headers("Authorization")) {
                halt(401, "User authorization")
            }

            val token:String = req.headers("Authorization").replace("Bearer ", "").replace("OAuth2 ", "")

            if(!tokens.containsValue(token)) {
                halt(401, "User authorization")
            }
        }
    }

    post(SERVICE_VERSION + "/auth", { req, res ->
        val bodyAsString = req.body()

        val credentials = JSON_ENGINE.fromJson(bodyAsString, Credentials::class.java)

        if(users[credentials.username].equals(credentials.password)) {
            val token = UUID.randomUUID().toString().replace("-", "")

            tokens.put(credentials.username, token)

            token
        } else {
            halt(401, "Wrong username/password")
        }
    })

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