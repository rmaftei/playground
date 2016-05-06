package org.rmaftei.service.services.game

import org.rmaftei.service.model.game.Game

interface GameService {

    fun getAllGames(): List<Game>
}