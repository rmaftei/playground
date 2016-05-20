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
import java.util.*
import kotlin.test.assertTrue

class GameServiceSpecs : Spek() {

    val jsonEngine = org.rmaftei.service.JSON_ENGINE

    init {
        given("a game api") {
            val PORT = "4567"
            val SERVICE_VERSION = org.rmaftei.service.SERVICE_VERSION
            val RESOURCE = org.rmaftei.service.GAME_PATH

            val URL = "http://localhost:$PORT/$SERVICE_VERSION/$RESOURCE"

            org.rmaftei.service.gameRepository.createGame(
                    BLGame(UUID.randomUUID().toString(), DateTime.now(), "Location 1", "Description 1", "user 1"))

            org.rmaftei.service.gameRepository.createGame(
                    BLGame(UUID.randomUUID().toString(), DateTime.now(), "Location 2", "Description 2", "user 2"))

            org.rmaftei.service.gameRepository.createGame(
                    BLGame(UUID.randomUUID().toString(), DateTime.now(), "Location 3", "Description 3", "user 3"))

            org.rmaftei.service.main(emptyArray())

            Spark.awaitInitialization()


            on("getting all the games") {
                val response = Unirest.get(URL)

                it("it should return with code 200") {
                    assertTrue(response.asJson().status === 200)
                }

                val games = jsonEngine.fromJson(response.asJson().body.toString(), mutableListOf<Game>().javaClass)

                it("it should return 3 games") {
                    assertTrue(games.size === 3)
                }
            }

            on("creating a game") {
                val response = Unirest.post(URL)
                        .body(jsonEngine.toJson(Game("", DateTime.now(), "Location new", "New Description", "user id")))

                it("it should return with code 200") {
                    assertTrue(response.asJson().status === 200)
                }

                val newGame = jsonEngine.fromJson(response.asJson().body.toString(), Game::class.java)

                it("should return the new game with id and user's id") {
                    assertTrue(newGame !== null)
                    assertTrue(newGame.id.isNotBlank())
                    assertTrue(newGame.createdBy.isNotBlank())
                }
            }
        }
    }
}