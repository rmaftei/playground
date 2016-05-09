package org.rmaftei.service.repositories

import org.joda.time.DateTime
import org.rmaftei.service.model.game.Game
import java.util.*

class ListGameRepository: GameRepository {
    var games = listOf(
            Game(UUID.randomUUID().toString(), DateTime.now(), "Location 1", "description 1", "admin"),
            Game(UUID.randomUUID().toString(), DateTime.now(), "Location 2", "description 2", "admin"),
            Game(UUID.randomUUID().toString(), DateTime.now(), "Location 3", "description 3", "admin")
    )

    override fun getAllGames(): List<Game> {
        return games
    }

    override fun createGame(game: Game) {
        games = games.plus(game)
    }

    override fun updateGame(game: Game) {
        val gameToRemove = findGame(game.id)

        games = games.minus(gameToRemove).plus(game)
    }

    override fun deleteGame(gameId: String) {
        games = games.minus(findGame(gameId))
    }

    override fun findGame(id: String): Game {
        return games.filter { game ->
            game.id == id
        }[0]
    }
}
