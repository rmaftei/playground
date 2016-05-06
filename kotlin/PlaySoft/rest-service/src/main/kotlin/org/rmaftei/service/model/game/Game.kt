package org.rmaftei.service.model.game

import org.joda.time.DateTime

data class Game(val id: String,
                val startTime: DateTime,
                val location: String,
                val description: String,
                val createdBy: String)
