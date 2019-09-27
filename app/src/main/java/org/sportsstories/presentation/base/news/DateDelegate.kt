package org.sportsstories.presentation.base.news

import android.view.ViewGroup
import org.sportsstories.R
import ru.touchin.adapters.ItemAdapterDelegate
import ru.touchin.roboswag.components.utils.UiUtils

class DateDelegate : ItemAdapterDelegate<DateViewHolder, NewsListItem.DateItem>() {

    override fun isForViewType(item: Any, adapterPosition: Int, collectionPosition: Int) =
            item is NewsListItem.DateItem

    override fun onCreateViewHolder(parent: ViewGroup) =
            DateViewHolder(UiUtils.inflate(R.layout.item_date, parent))

    override fun onBindViewHolder(
            holder: DateViewHolder,
            item: NewsListItem.DateItem,
            adapterPosition: Int,
            collectionPosition: Int,
            payloads: MutableList<Any>
    ) = holder.bind(item)

}
