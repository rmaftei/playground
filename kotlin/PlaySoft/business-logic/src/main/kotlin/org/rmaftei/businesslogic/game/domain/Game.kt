package org.rmaftei.businesslogic.game.domain

import org.joda.time.DateTime

data class Game(val id: String,
                val startGame: DateTime,
                val location: String,
                val description: String,
                val createdBy: String)