package org.sportsstories.presentation.base.news

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.sportsstories.extensions.toDateString
import ru.touchin.extensions.context

class DateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(item: NewsListItem.DateItem) {
        (itemView as TextView).text = item.date.toDateString(context)
    }

}
