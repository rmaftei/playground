package org.rmaftei.json

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import org.rmaftei.model.game.Game
import java.lang.reflect.Type

class GameSerializer: JsonSerializer<Game> {
    override fun serialize(src: Game?, p1: Type?, p2: JsonSerializationContext?): JsonElement? {
        val jsObject = JsonObject()

        if(null != src) {
            jsObject.addProperty("id", src.id)
            jsObject.addProperty("startTime", src.startTime.toDateTime().toString())
            jsObject.addProperty("location", src.location)
            jsObject.addProperty("description", src.description)
            jsObject.addProperty("createdBy", src.createdBy)
        }

        return jsObject
    }

}