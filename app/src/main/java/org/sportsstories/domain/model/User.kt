package org.sportsstories.domain.model

class User(
        val sessionId: String,
        id: Int,
        accessToken: String
) : VkUser(id, accessToken)
