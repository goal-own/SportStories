package org.sportsstories.data.api.json_adapters

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.util.UUID

class UUIDConverter {

    @ToJson
    fun toJson(uuid: UUID) = uuid.toString()

    @FromJson
    fun fromJson(uuidString: String) = UUID.fromString(uuidString)

}
