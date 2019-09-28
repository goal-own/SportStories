package org.sportsstories.domain.model

import java.util.UUID

class User(
        val sessionId: UUID,
        id: Int,
        accessToken: String
) : VkUser(id, accessToken)
