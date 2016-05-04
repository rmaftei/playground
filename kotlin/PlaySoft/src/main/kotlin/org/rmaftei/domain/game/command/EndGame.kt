package org.rmaftei.domain.game.command

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier

data class EndGame(@TargetAggregateIdentifier val gameId: String)