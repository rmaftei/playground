package org.rmaftei.service.game

import com.google.gson.GsonBuilder
import com.mashape.unirest.http.Unirest
import org.jetbrains.spek.api.Spek
import org.junit.AfterClass
import org.junit.BeforeClass
import org.rmaftei.service.json.GameDeserializer
import org.rmaftei.service.json.GameSerializer
import org.rmaftei.service.model.game.Game
import spark.Spark
import kotlin.test.assertTrue

class GameServiceSpecs : Spek() {

    val gson = GsonBuilder()
            .registerTypeAdapter(Game::class.java, GameSerializer())
            .registerTypeAdapter(Game::class.java, GameDeserializer())
            .create()

    companion object {
        @BeforeClass
        fun beforeAllTests() {
            org.rmaftei.service.main(emptyArray())
        }

        @AfterClass
        fun afterAllTests() {
            Spark.stop()
        }
    }

    init {
        given("A game api") {
            val PORT = "4567"
            val SERVICE_VERSION = org.rmaftei.service.SERVICE_VERSION
            val RESOURCE = org.rmaftei.service.GAME_PATH

            val URL = "http://localhost:$PORT/$SERVICE_VERSION/$RESOURCE"


            on("Getting all the games") {

                val response = Unirest.get(URL)

                assertTrue(response.asJson().status === 200)

                val games = gson.fromJson(response.asJson().body.toString(), mutableListOf<Game>().javaClass)

                assertTrue(games.size === 3)
            }
        }
    }
}