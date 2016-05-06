package org.rmaftei.service.services.game

import org.joda.time.DateTime
import org.rmaftei.service.model.game.Game
import java.util.*

class SimpleGameService: GameService {

    override fun createGame(game: Game) {
        throw UnsupportedOperationException()
    }

    override fun addGame(game: Game) {
        throw UnsupportedOperationException()
    }

    override fun updateGame(game: Game) {
        throw UnsupportedOperationException()
    }

    override fun endGame(gameId: String) {
        throw UnsupportedOperationException()
    }

    override fun getAllGames(): List<Game> {
        return listOf(
                Game("1", DateTime.now(), "Location 1", "description 1", UUID.randomUUID().toString()),
                Game("2", DateTime.now(), "Location 2", "description 2", UUID.randomUUID().toString()),
                Game("3", DateTime.now(), "Location 3", "description 3", UUID.randomUUID().toString())
        )
    }
}