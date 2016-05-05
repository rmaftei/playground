package org.rmaftei.services.game

import org.rmaftei.model.game.Game

interface GameService {

    fun getAllGames(): List<Game>
}