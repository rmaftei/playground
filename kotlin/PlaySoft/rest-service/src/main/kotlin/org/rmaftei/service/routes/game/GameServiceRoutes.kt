package org.rmaftei.service.routes.game

import com.google.gson.GsonBuilder
import org.rmaftei.service.json.GameDeserializer
import org.rmaftei.service.json.GameSerializer
import org.rmaftei.service.model.game.Game
import org.rmaftei.service.services.game.GameService
import spark.Request
import spark.Response
import spark.Route

object GameServiceRoutes {

    private val gson = GsonBuilder()
            .registerTypeAdapter(Game::class.java, GameSerializer())
            .registerTypeAdapter(Game::class.java, GameDeserializer())
            .create()

    class GetAll(private val gameService: GameService) : Route {
        override fun handle(req: Request, res: Response): Any {
            res.type("text/json")
            return gson.toJson(gameService.getAllGames())
        }
    }
}