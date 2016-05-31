package org.rmaftei.service.json

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import org.joda.time.DateTime
import org.rmaftei.service.model.game.Game
import java.lang.reflect.Type

class GameDeserializer: JsonDeserializer<Game> {
    override fun deserialize(src: JsonElement?, p1: Type?, p2: JsonDeserializationContext?): Game? {
        if(null != src) {
            val jsonObject = src.getAsJsonObject()

            val id = if(jsonObject.get("id") != null) jsonObject.get("id").asString else ""
            val startTime = DateTime.parse(jsonObject.get("startTime").asString)
            val location = jsonObject.get("location").asString
            val description = jsonObject.get("description").asString
            val createdBy = if(jsonObject.get("createdBy") != null) jsonObject.get("id").asString else ""

            return Game(id, startTime, location, description, createdBy)
        }

        return null
    }

}