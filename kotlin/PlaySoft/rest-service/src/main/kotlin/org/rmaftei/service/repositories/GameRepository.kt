package org.rmaftei.service.repositories

import org.rmaftei.service.model.game.Game
import org.rmaftei.service.Maybe

interface GameRepository {

    fun getAllGames(): List<Game>

    fun createGame(game: Game)

    fun updateGame(game: Game)

    fun deleteGame(gameId: String)

    fun findGame(id: String): Maybe<Game>
}