package org.rmaftei.service.repositories

import org.joda.time.DateTime
import org.rmaftei.service.model.game.Game
import org.rmaftei.service.Maybe
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
        val maybeGame = findGame(game.id)

        if(maybeGame != Maybe.None) {
            games = games.minus(maybeGame.get()).plus(game)
        }
    }

    override fun deleteGame(gameId: String) {
        val maybeGame = findGame(gameId)

        if(maybeGame != Maybe.None) {
            games = games.minus(maybeGame.get())
        }
    }

    override fun findGame(id: String): Maybe<Game> {
        val found = games.filter { game ->
            game.id == id
        }

        if(found.size == 0) {
            return Maybe.None
        }

        return Maybe.Just(found[0])
    }
}
