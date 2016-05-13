package org.rmaftei.service.routes.game

import com.google.gson.GsonBuilder
import org.rmaftei.businesslogic.game.GameApplication
import org.rmaftei.businesslogic.game.domain.Game as BLGame
import org.rmaftei.service.json.GameDeserializer
import org.rmaftei.service.json.GameSerializer
import org.rmaftei.service.model.game.Game
import spark.Request
import spark.Response
import spark.Route

object GameServiceRoutes {

    private val gson = GsonBuilder()
            .registerTypeAdapter(Game::class.java, GameSerializer())
            .registerTypeAdapter(Game::class.java, GameDeserializer())
            .create()

    class GetAll(private val gameApplication: GameApplication) : Route {
        override fun handle(req: Request, res: Response): Any {
            res.type("text/json")

            val games:List<BLGame> = gameApplication.getAllGames();

            val gamesView = games.map { game ->
                Game(game.id, game.startGame, game.location, game.description, game.createdBy)
            }

            return gson.toJson(gamesView)
        }
    }
}