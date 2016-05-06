package org.rmaftei.service.services.game

import org.rmaftei.service.model.game.Game

interface GameService {

    fun getAllGames(): List<Game>

    fun addGame(game:Game)

    fun createGame(game:Game)

    fun updateGame(game: Game)

    fun endGame(gameId: String)
}