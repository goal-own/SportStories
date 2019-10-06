package org.sportsstories.presentation.base.stories

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import org.sportsstories.R
import ru.touchin.adapters.DelegationListAdapter
import ru.touchin.adapters.ItemAdapterDelegate
import ru.touchin.roboswag.components.utils.UiUtils

class StoriesAdapter : DelegationListAdapter<StoriesItem>(CALLBACK) {

    companion object {
        val CALLBACK = object : DiffUtil.ItemCallback<StoriesItem>() {
            override fun areItemsTheSame(oldItem: StoriesItem, newItem: StoriesItem) =
                    oldItem is StoriesItem.FriendsStories && newItem is StoriesItem.FriendsStories &&
                            oldItem.stories.userId == newItem.stories.userId

            override fun areContentsTheSame(oldItem: StoriesItem, newItem: StoriesItem) =
                    oldItem is StoriesItem.FriendsStories && newItem is StoriesItem.FriendsStories &&
                            oldItem.stories.userId == newItem.stories.userId
        }
    }

    init {
        addDelegate(StoriesDelegate())
        addDelegate(object : ItemAdapterDelegate<AddStoriesViewHolder, StoriesItem.AddStories>() {
            override fun isForViewType(item: Any, adapterPosition: Int, collectionPosition: Int) =
                    item is StoriesItem.AddStories

            override fun onCreateViewHolder(parent: ViewGroup) =
                    AddStoriesViewHolder(UiUtils.inflate(R.layout.item_stories, parent))

            override fun onBindViewHolder(
                    holder: AddStoriesViewHolder,
                    item: StoriesItem.AddStories,
                    adapterPosition: Int,
                    collectionPosition: Int,
                    payloads: MutableList<Any>
            ) = Unit
        })
    }

}
