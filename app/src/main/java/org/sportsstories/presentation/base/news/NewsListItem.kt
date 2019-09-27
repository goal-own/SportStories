package org.sportsstories.presentation.base.news

import org.joda.time.LocalDate
import org.sportsstories.domain.model.NewsPreview

sealed class NewsListItem {

    class NewsItem(
            val news: NewsPreview
    ) : NewsListItem()

    class DateItem(
            val date: LocalDate
    ) : NewsListItem()

}
