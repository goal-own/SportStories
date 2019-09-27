package org.sportsstories.domain.repository

import org.joda.time.DateTime
import org.sportsstories.domain.model.NewsPreview
import org.sportsstories.utils.async
import javax.inject.Inject

class NewsRepository @Inject constructor() {

    fun fetchNewsPreviewsAsync() = async {
        newsMock
    }

    private val newsMock = listOf(
            NewsPreview(
                    1,
                    DateTime.now(),
                    "Новость",
                    2
            ),
            NewsPreview(
                    1,
                    DateTime.now(),
                    "Новость12",
                    2
            ),
            NewsPreview(
                    2,
                    DateTime.now().minusDays(2),
                    "Новость 2",
                    2
            ),
            NewsPreview(
                    3,
                    DateTime.now().minusDays(2),
                    "Новость 3",
                    2
            ),
            NewsPreview(
                    4,
                    DateTime.now().minusDays(10),
                    "Новость 4",
                    2
            )
    )

}
