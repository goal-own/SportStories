package org.sportsstories.presentation.base.stories

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import org.sportsstories.R
import org.sportsstories.presentation.views.StoriesView
import ru.touchin.extensions.getString

class StoriesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(item: StoriesItem.FriendsStories) {
        (itemView as StoriesView).bind(
                item.stories.previewUrl,
                getString(R.string.stories_user_name_format, item.stories.userFirstName, item.stories.userSecondName)
        )
    }

}
