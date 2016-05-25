package org.rmaftei.businesslogic.game

import org.rmaftei.businesslogic.game.domain.Game
import org.rmaftei.businesslogic.game.port.GameRepository
import org.rmaftei.businesslogic.game.util.RecordDoesNotExists
import org.rmaftei.service.Maybe
import java.util.*


class GameApplication(private val gameRepository: GameRepository) {

    fun createGame(game: Game): Game {
        val newGame = game.copy(id = UUID.randomUUID().toString())

        gameRepository.createGame(newGame)

        return newGame
    }

    fun updateGame(game: Game): Game {
        val originalGame = gameRepository.findGame(game.id)

        if(originalGame != Maybe.None) {
            val updatedGame = originalGame.get()
                    .copy(
                            location = game.location,
                            description = game.description,
                            startGame = game.startGame
                    )

            return gameRepository.updateGame(updatedGame)
        }

        throw RecordDoesNotExists.Game(game.id)
    }

    fun deleteGame(gameId: String) {
        gameRepository.deleteGame(gameId)
    }

    fun getAllGames(): List<Game> {
        return gameRepository.getAllGames()
    }

    fun getGame(gameId: String): Maybe<Game> {
        return gameRepository.findGame(gameId)
    }
}