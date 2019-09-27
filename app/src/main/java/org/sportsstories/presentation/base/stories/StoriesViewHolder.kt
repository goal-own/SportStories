package org.sportsstories.presentation.base.stories

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import org.sportsstories.R
import org.sportsstories.domain.model.Stories
import org.sportsstories.presentation.views.StoriesView
import ru.touchin.extensions.getString

class StoriesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(stories: Stories) {
        (itemView as StoriesView).bind(
                stories.previewUrl,
                getString(R.string.stories_user_name_format, stories.userFirstName, stories.userSecondName)
        )
    }

}
