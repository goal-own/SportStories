package org.sportsstories.domain.model

import org.joda.time.DateTime

data class NewsPreview(
        val id: Int,
        val date: DateTime,
        val title: String,
        val reactionsCount: Int
)
