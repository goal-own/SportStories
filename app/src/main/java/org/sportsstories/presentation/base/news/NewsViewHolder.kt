package org.sportsstories.presentation.base.news

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_news_preview.view.item_news_preview_reactions_count
import kotlinx.android.synthetic.main.item_news_preview.view.item_news_preview_time
import kotlinx.android.synthetic.main.item_news_preview.view.item_news_preview_title
import org.sportsstories.extensions.toTimeString

class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(item: NewsListItem.NewsItem) {
        with(itemView) {
            item_news_preview_time.text = item.news.date.toTimeString(context)
            item_news_preview_title.text = item.news.title
            item_news_preview_reactions_count.text = item.news.reactionsCount.toString()
        }
    }

}
