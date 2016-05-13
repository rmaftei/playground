package org.rmaftei.businesslogic.game

import org.rmaftei.businesslogic.game.domain.Game
import org.rmaftei.businesslogic.game.repository.GameRepository
import org.rmaftei.service.Maybe
import java.util.*


class GameApplication(private val gameRepository: GameRepository) {


    fun createGame(game: Game) {
        val newGame = game.copy(id = UUID.randomUUID().toString(), createdBy = "admin")

        gameRepository.createGame(newGame)
    }

    fun updateGame(game: Game) {
        val originalGame = gameRepository.findGame(game.id)

        if(originalGame != Maybe.None) {
            val updatedGame = originalGame.get()
                    .copy(
                            location = game.location,
                            description = game.description,
                            startGame = game.startGame
                    )

            gameRepository.updateGame(updatedGame)
        }
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