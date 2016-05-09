package org.rmaftei.service.services.game

import org.rmaftei.service.model.game.Game

interface GameService {

    fun getAllGames(): List<Game>

    fun createGame(game:Game)

    fun updateGame(game: Game)

    fun getGame(id: String): Game

    fun deleteGame(id: String)
}