package org.sportsstories.presentation.base.stories

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.view_stories.view.item_stories_text
import org.sportsstories.R
import ru.touchin.extensions.getString

class AddStoriesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    init {
        itemView.item_stories_text.text = getString(R.string.stories_add)
    }

}
