package org.sportsstories.presentation.base.stories

import androidx.recyclerview.widget.DiffUtil
import org.sportsstories.domain.model.Stories
import ru.touchin.adapters.DelegationListAdapter

class StoriesAdapter : DelegationListAdapter<Stories>(CALLBACK) {

    companion object {
        val CALLBACK = object : DiffUtil.ItemCallback<Stories>() {
            override fun areItemsTheSame(oldItem: Stories, newItem: Stories) =
                    oldItem.userId == newItem.userId

            override fun areContentsTheSame(oldItem: Stories, newItem: Stories) =
                    oldItem.userId == newItem.userId
        }
    }

    init {
        addDelegate(StoriesDelegate())
    }

}
