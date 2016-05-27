package org.rmaftei.businesslogic.game


import org.joda.time.DateTime
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.rmaftei.businesslogic.game.domain.Game
import org.rmaftei.businesslogic.game.port.GamesPort
import org.rmaftei.businesslogic.game.util.RecordDoesNotExists
import org.rmaftei.service.Maybe
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class GameApplicationTest {

    val repositoryMock = mock(GamesPort::class.java)

    val gameApplication = GameApplication(repositoryMock)

    val predefinedId = "1234"
    val now = DateTime.now()
    val location = "location test"
    val description = "description test"
    val predefinedUser = "admin"

    @Test
    fun test_create_game() {
        val game = Game(predefinedId, now, location, description, predefinedUser)

        `when`(repositoryMock.createGame(game)).then {
            game
        }

        val newGame = gameApplication.createGame(game)

        assertNotNull(newGame)
        assertNotNull(newGame.id)
        assertTrue(newGame.id.isNotBlank())
        assertTrue(newGame.id.isNotEmpty())
        assertTrue(newGame.id !== predefinedId)

        assertTrue(newGame.startGame === now)
        assertTrue(newGame.location === location)
        assertTrue(newGame.description === description)
        assertTrue(newGame.createdBy === predefinedUser)
    }

    @Test
    fun test_update_game() {
        val game = Game(predefinedId, now, location, description, predefinedUser)
        val updateGame = game.copy(description = game.description.plus(" -- updated"))

        `when`(repositoryMock.findGame(predefinedId)).then {
            Maybe.Just(game)
        }

        `when`(repositoryMock.updateGame(updateGame)).then {
            updateGame
        }


        val updatedGame = gameApplication.updateGame(updateGame)

        assertNotNull(updatedGame)
        assertNotNull(updatedGame.id)
        assertTrue(updatedGame.id.isNotBlank())
        assertTrue(updatedGame.id.isNotEmpty())
        assertTrue(updatedGame.id == predefinedId)

        assertTrue(updatedGame.startGame === now)
        assertTrue(updatedGame.location === location)
        assertTrue(updatedGame.description.equals(description.plus(" -- updated")))
        assertTrue(updatedGame.createdBy === predefinedUser)
    }


    @Test(expected = RecordDoesNotExists.Game::class)
    fun test_update_game_that_does_not_exists() {
        `when`(repositoryMock.findGame(predefinedId)).then {
            Maybe.None
        }

        val game = Game(predefinedId, now, location, description, predefinedUser)

        gameApplication.updateGame(game)

    }

    @Test
    fun test_get_game_by_id() {
        `when`(repositoryMock.findGame(predefinedId)).then {
            Maybe.Just(Game(predefinedId, now, location, description, predefinedUser))
        }

        val maybeGame = gameApplication.getGame(predefinedId)
        val game = maybeGame.get()

        assertNotNull(game)
        assertNotNull(game.id)
        assertTrue(game.id.isNotBlank())
        assertTrue(game.id.isNotEmpty())
        assertTrue(game.id === predefinedId)

        assertTrue(game.startGame === now)
        assertTrue(game.location === location)
        assertTrue(game.description === description)
        assertTrue(game.createdBy === predefinedUser)
    }
}

