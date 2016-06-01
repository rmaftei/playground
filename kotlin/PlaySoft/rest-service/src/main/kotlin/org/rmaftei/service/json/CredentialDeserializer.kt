package org.rmaftei.service.json

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import org.rmaftei.service.model.user.Credentials
import java.lang.reflect.Type

class CredentialDeserializer: JsonDeserializer<Credentials> {
    override fun deserialize(src: JsonElement?, p1: Type?, p2: JsonDeserializationContext?): Credentials? {
        if(null != src) {
            val jsonObject = src.getAsJsonObject()

            val username = jsonObject.get("username").asString
            val password = jsonObject.get("password").asString

            return Credentials(username, password)
        }

        return null
    }
}