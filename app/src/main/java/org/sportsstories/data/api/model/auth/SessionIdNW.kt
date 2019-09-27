package org.sportsstories.data.api.model.auth

import com.squareup.moshi.JsonClass
import java.util.UUID

@JsonClass(generateAdapter = true)
class SessionIdNW(
        val sessionId: UUID
)
