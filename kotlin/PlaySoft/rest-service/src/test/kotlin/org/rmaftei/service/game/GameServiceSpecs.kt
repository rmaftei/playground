package org.rmaftei.service.game

import org.jetbrains.spek.api.Spek

class GameServiceSpecs : Spek() {
//    init {
//        val gameRepository = EasyMock.createMock(GameRepository::class.java)
//
//        val id = "id "
//        val startTime = DateTime.now()
//        val location = "location"
//        val description = "description"
//        val createdBy = "user"
//
//        EasyMock.expect(gameRepository.getAllGames())
//                .andReturn(listOf(
//                        Game(id + "1", startTime, location, description, createdBy),
//                        Game(id + "2", startTime, location, description, createdBy),
//                        Game(id + "3", startTime, location, description, createdBy))).times(1)
//
//        EasyMock.replay(gameRepository)
//
//        given("A game service") {
//            val gameService = SimpleGameService(gameRepository)
//
//            on("getting all the games") {
//                val games = gameService.getAllGames()
//
//                it("it should be 3 games") {
//                    assertEquals(games.size, 3)
//                }
//            }
//        }
//    }
}