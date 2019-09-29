package org.sportsstories.presentation.base.stories

import org.sportsstories.domain.model.Stories

sealed class StoriesItem {

    class FriendsStories(
            val stories: Stories
    ) : StoriesItem()

    object AddStories : StoriesItem()

}