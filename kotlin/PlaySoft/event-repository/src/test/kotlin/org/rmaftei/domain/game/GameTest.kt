package org.rmaftei.domain.game

import org.axonframework.test.FixtureConfiguration
import org.axonframework.test.Fixtures
import org.joda.time.DateTime
import org.junit.Before
import org.junit.Test
import org.rmaftei.domain.game.aggregator.Game
import org.rmaftei.domain.game.command.CreateGame
import org.rmaftei.domain.game.command.EndGame
import org.rmaftei.domain.game.event.GameCreated
import org.rmaftei.domain.game.event.GameEnded

class GameTest {

    var fixture: FixtureConfiguration<*> ? = null

    val id = "unique id"
    val startDate = DateTime.now()
    val location = "Location #1"
    val description = "Just a simple game"
    val userId = "user id"

    val createGame = CreateGame(
            id,
            startDate,
            location,
            description,
            userId)

    val gameCreated = GameCreated(
            id,
            startDate,
            location,
            description,
            userId)

    @Before
    fun setUp() {
        fixture = Fixtures.newGivenWhenThenFixture(Game::class.java)
    }

    @Test
    fun createGameTest() {
        fixture!!.given().
                `when`(createGame)
                .expectEvents(gameCreated)
    }

    @Test
    fun endGameTest() {
        fixture!!.given(gameCreated)
                .`when`(EndGame(id))
                .expectEvents(GameEnded(id))
    }
}