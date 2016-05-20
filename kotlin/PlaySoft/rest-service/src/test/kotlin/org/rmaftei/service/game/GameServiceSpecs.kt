package org.rmaftei.service.game

import com.google.gson.GsonBuilder
import com.mashape.unirest.http.Unirest
import org.jetbrains.spek.api.Spek
import org.joda.time.DateTime
import org.rmaftei.service.json.GameDeserializer
import org.rmaftei.service.json.GameSerializer
import org.rmaftei.service.model.game.Game
import org.rmaftei.businesslogic.game.domain.Game as BLGame
import spark.Spark
import kotlin.test.assertTrue

class GameServiceSpecs : Spek() {

    val gson = GsonBuilder()
            .registerTypeAdapter(Game::class.java, GameSerializer())
            .registerTypeAdapter(Game::class.java, GameDeserializer())
            .create()

    init {
        given("A game api") {
            val PORT = "4567"
            val SERVICE_VERSION = org.rmaftei.service.SERVICE_VERSION
            val RESOURCE = org.rmaftei.service.GAME_PATH

            val URL = "http://localhost:$PORT/$SERVICE_VERSION/$RESOURCE"

            org.rmaftei.service.gameRepository.createGame(
                    BLGame("", DateTime.now(), "Location 1", "Description 1","user 1"))

            org.rmaftei.service.gameRepository.createGame(
                    BLGame("", DateTime.now(), "Location 2", "Description 2","user 2"))

            org.rmaftei.service.gameRepository.createGame(
                    BLGame("", DateTime.now(), "Location 3", "Description 3","user 3"))

            org.rmaftei.service.main(emptyArray())

            Spark.awaitInitialization()


            on("Getting all the games") {

                val response = Unirest.get(URL)

                assertTrue(response.asJson().status === 200)

                val games = gson.fromJson(response.asJson().body.toString(), mutableListOf<Game>().javaClass)

                assertTrue(games.size === 3)
            }
        }
    }
}