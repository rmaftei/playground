package org.rmaftei.domain.game.event

import org.joda.time.DateTime

data class GameCreated(val gameId: String,
                       val startGame: DateTime,
                       val location: String,
                       val description: String,
                       val createdBy: String)