package org.rmaftei.service.services.game

import org.rmaftei.service.model.game.Game
import org.rmaftei.service.Maybe
import org.rmaftei.service.repositories.GameRepository
import java.util.*

class SimpleGameService(val gameRepository: GameRepository): GameService {
    override fun deleteGame(id: String) {
        gameRepository.deleteGame(id)
    }

    override fun getGame(id: String): Maybe<Game> {
        return gameRepository.findGame(id)
    }

    override fun createGame(game: Game) {
        val newGame = game.copy(id = UUID.randomUUID().toString(), createdBy = "admin")

        gameRepository.createGame(newGame)
    }

    override fun updateGame(game: Game) {
        val originalGame = gameRepository.findGame(game.id)

        if(originalGame != Maybe.None) {
            val updatedGame = originalGame.get()
                    .copy(
                            location = game.location,
                            description = game.description,
                            startTime = game.startTime
                    )

            gameRepository.updateGame(updatedGame)
        }
    }

    override fun getAllGames(): List<Game> {
        return gameRepository.getAllGames()
    }
}