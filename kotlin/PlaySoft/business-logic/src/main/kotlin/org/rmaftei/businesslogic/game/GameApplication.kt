package org.rmaftei.businesslogic.game

import org.rmaftei.businesslogic.game.domain.Game
import org.rmaftei.businesslogic.game.port.GamesPort
import org.rmaftei.businesslogic.game.util.RecordDoesNotExists
import org.rmaftei.service.Maybe
import java.util.*


class GameApplication(private val gamesPort: GamesPort) {

    fun createGame(game: Game): Game {
        val newGame = game.copy(id = UUID.randomUUID().toString())

        gamesPort.createGame(newGame)

        return newGame
    }

    fun updateGame(game: Game): Game {
        val originalGame = gamesPort.findGame(game.id)

        if(originalGame != Maybe.None) {
            val updatedGame = originalGame.get()
                    .copy(
                            location = game.location,
                            description = game.description,
                            startGame = game.startGame
                    )

            return gamesPort.updateGame(updatedGame)
        }

        throw RecordDoesNotExists.Game(game.id)
    }

    fun deleteGame(gameId: String) {
        gamesPort.deleteGame(gameId)
    }

    fun getAllGames(): List<Game> {
        return gamesPort.getAllGames()
    }

    fun getGame(gameId: String): Maybe<Game> {
        return gamesPort.findGame(gameId)
    }
}