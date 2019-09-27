package org.sportsstories.domain.model

import org.joda.time.DateTime

data class Game(
        val firstTeam: TeamShort,
        val secondTeam: TeamShort,
        val status: GameStatus,
        val startTime: DateTime,
        val firstTeamScore: Int? = null,
        val secondTeamScore: Int? = null
)
