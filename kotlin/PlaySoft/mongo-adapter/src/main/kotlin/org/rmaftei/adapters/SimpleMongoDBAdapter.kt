package org.rmaftei.adapters

import com.mongodb.BasicDBObject
import com.mongodb.MongoClient
import org.bson.Document
import org.joda.time.DateTime
import org.rmaftei.businesslogic.game.domain.Game
import org.rmaftei.businesslogic.game.port.GamesPort
import org.rmaftei.service.Maybe

class SimpleMongoDBAdapter(address:String = "localhost", port: Int = 27017, database: String = "playsoft"): GamesPort {

    val mongoClient = MongoClient(address, port)
    val db = mongoClient.getDatabase(database)
    val coll = db.getCollection("games")

    override fun getAllGames(): List<Game> {
        return coll.find().map { game ->
            Game(game.getString("id"),
                    DateTime(game.getDate("startGame")),
                    game.getString("location"),
                    game.getString("description"),
                    game.getString("createdBy"))
        }.toCollection(mutableListOf<Game>())
    }

    override fun createGame(game: Game): Game {
        val newGame = Document()
                .append("id", game.id)
                .append("startGame", game.startGame)
                .append("location", game.location)
                .append("description", game.description)
                .append("createdBy", game.createdBy)

        coll.insertOne(newGame)

        return game
    }

    override fun updateGame(game: Game): Game {
        val updateGame = Document()
                .append("id", game.id)
                .append("startGame", game.startGame)
                .append("location", game.location)
                .append("description", game.description)
                .append("createdBy", game.createdBy)

        coll.updateOne(BasicDBObject("_id", game.id), updateGame)

        return game;
    }

    override fun deleteGame(gameId: String) {
        coll.deleteOne(BasicDBObject("_id", gameId))
    }

    override fun findGame(id: String): Maybe<Game> {
        val foundGames = coll.find(BasicDBObject("_id", id)).map { game ->
            Game(game.getString("id"),
                    DateTime(game.getDate("startGame")),
                    game.getString("location"),
                    game.getString("description"),
                    game.getString("createdBy"))
        }.toCollection(mutableListOf<Game>())

        return Maybe.Just(foundGames[0])
    }
}