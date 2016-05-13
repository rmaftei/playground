package org.rmaftei.domain.game.command

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier
import org.joda.time.DateTime

data class CreateGame(@TargetAggregateIdentifier val gameId: String,
                      val startGame: DateTime,
                      val location: String,
                      val description: String,
                      val createdBy: String)