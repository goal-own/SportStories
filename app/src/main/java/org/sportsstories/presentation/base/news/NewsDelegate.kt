package org.sportsstories.presentation.base.news

import android.view.ViewGroup
import org.sportsstories.R
import ru.touchin.adapters.ItemAdapterDelegate
import ru.touchin.roboswag.components.utils.UiUtils

class NewsDelegate : ItemAdapterDelegate<NewsViewHolder, NewsListItem.NewsItem>() {

    override fun isForViewType(item: Any, adapterPosition: Int, collectionPosition: Int) =
            item is NewsListItem.NewsItem

    override fun onCreateViewHolder(parent: ViewGroup) =
            NewsViewHolder(UiUtils.inflate(R.layout.item_news_preview, parent))

    override fun onBindViewHolder(
            holder: NewsViewHolder,
            item: NewsListItem.NewsItem,
            adapterPosition: Int,
            collectionPosition: Int,
            payloads: MutableList<Any>
    ) = holder.bind(item)
}