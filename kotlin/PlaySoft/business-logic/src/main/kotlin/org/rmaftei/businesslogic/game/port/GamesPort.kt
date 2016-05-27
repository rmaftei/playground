package org.rmaftei.businesslogic.game.port

import org.rmaftei.businesslogic.game.domain.Game
import org.rmaftei.service.Maybe

interface GamesPort {

    fun getAllGames(): List<Game>

    fun createGame(game: Game): Game

    fun updateGame(game: Game): Game

    fun deleteGame(gameId: String)

    fun findGame(id: String): Maybe<Game>

}