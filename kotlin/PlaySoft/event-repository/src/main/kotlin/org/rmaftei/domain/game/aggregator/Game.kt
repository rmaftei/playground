package org.rmaftei.domain.game.aggregator

import org.axonframework.commandhandling.annotation.CommandHandler
import org.axonframework.eventhandling.annotation.EventHandler
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot
import org.axonframework.eventsourcing.annotation.AggregateIdentifier
import org.rmaftei.domain.game.command.CreateGame
import org.rmaftei.domain.game.command.EndGame
import org.rmaftei.domain.game.event.GameCreated
import org.rmaftei.domain.game.event.GameEnded

class Game: AbstractAnnotatedAggregateRoot<CreateGame> {

    @AggregateIdentifier
    var id: String ?= null

    constructor() {}

    @CommandHandler
    constructor(createGame: CreateGame) {
        super.apply(GameCreated(createGame.gameId,
                createGame.startGame,
                createGame.location,
                createGame.description,
                createGame.createdBy))
    }

    @CommandHandler
    fun endGame(endGame: EndGame) {
        super.apply(GameEnded(endGame.gameId))
    }

    @EventHandler
    fun on(event: GameCreated) {
        this.id = event.gameId
    }

}