package org.sportsstories.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.sportsstories.domain.model.Game
import org.sportsstories.domain.repository.GamesRepository
import org.sportsstories.domain.repository.NewsRepository
import org.sportsstories.domain.repository.StoriesRepository
import org.sportsstories.internal.routing.navigation.root.RootScreenNavigation
import org.sportsstories.lifecycle.event.ContentEvent
import org.sportsstories.lifecycle_coroutines.CoroutinesViewModel
import org.sportsstories.presentation.base.news.NewsListItem
import org.sportsstories.presentation.base.stories.StoriesItem
import org.sportsstories.utils.async
import javax.inject.Inject

class MainScreenViewModel @Inject constructor(
        private val rootScreenNavigation: RootScreenNavigation,
        private val storiesRepository: StoriesRepository,
        private val gamesRepository: GamesRepository,
        private val newsRepository: NewsRepository
) : CoroutinesViewModel() {

    val stories: LiveData<ContentEvent<List<StoriesItem>>>
        get() = _stories

    val games: LiveData<ContentEvent<List<Game>>>
        get() = _games

    val news: LiveData<ContentEvent<List<NewsListItem>>>
        get() = _news

    private val _stories: MutableLiveData<ContentEvent<List<StoriesItem>>> = MutableLiveData()
    private val _games: MutableLiveData<ContentEvent<List<Game>>> = MutableLiveData()
    private val _news: MutableLiveData<ContentEvent<List<NewsListItem>>> = MutableLiveData()

    fun fetchStories() {
        storiesRepository.getStoriesAsync().dispatchTo(_stories)
    }

    fun fetchGames() {
        gamesRepository.getGamesAsync().dispatchTo(_games)
    }

    fun openShootStoriesScreen() {
        rootScreenNavigation.start.openShootStoriesScreen()
    }

    fun fetchInitialNews(withDates: Boolean = true) {
        async {
            if (withDates) newsRepository
                    .fetchNewsPreviewsAsync()
                    .await()
                    .groupBy { it.date.toLocalDate() }
                    .flatMap {
                        listOf(NewsListItem.DateItem(it.key)) + it.value.map(NewsListItem::NewsItem)
                    }
            else newsRepository
                    .fetchNewsPreviewsAsync()
                    .await()
                    .map(NewsListItem::NewsItem)
        }.dispatchTo(_news)
    }

}
