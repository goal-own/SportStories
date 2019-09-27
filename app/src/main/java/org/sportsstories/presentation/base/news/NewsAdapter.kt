package org.sportsstories.presentation.base.news

import androidx.recyclerview.widget.DiffUtil
import ru.touchin.adapters.DelegationListAdapter

class NewsAdapter : DelegationListAdapter<NewsListItem>(CALLBACK) {

    companion object {
        val CALLBACK = object : DiffUtil.ItemCallback<NewsListItem>() {
            override fun areItemsTheSame(oldItem: NewsListItem, newItem: NewsListItem) = when {
                oldItem is NewsListItem.NewsItem && newItem is NewsListItem.NewsItem -> oldItem.news.id == newItem.news.id
                oldItem is NewsListItem.DateItem && newItem is NewsListItem.DateItem -> oldItem.date == newItem.date
                else -> false
            }

            override fun areContentsTheSame(oldItem: NewsListItem, newItem: NewsListItem) = when {
                oldItem is NewsListItem.NewsItem && newItem is NewsListItem.NewsItem -> {
                    oldItem.news.date == newItem.news.date &&
                            oldItem.news.title == newItem.news.title &&
                            oldItem.news.reactionsCount == newItem.news.reactionsCount
                }
                oldItem is NewsListItem.DateItem && newItem is NewsListItem.DateItem -> {
                    oldItem.date == newItem.date
                }
                else -> false
            }

        }
    }

    init {
        addDelegate(NewsDelegate())
        addDelegate(DateDelegate())
    }


}