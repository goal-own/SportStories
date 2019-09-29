package org.sportsstories.presentation.base.stories

import android.view.ViewGroup
import org.sportsstories.R
import ru.touchin.adapters.ItemAdapterDelegate
import ru.touchin.roboswag.components.utils.UiUtils

class StoriesDelegate : ItemAdapterDelegate<StoriesViewHolder, StoriesItem.FriendsStories>() {

    override fun isForViewType(item: Any, adapterPosition: Int, collectionPosition: Int) =
            item is StoriesItem.FriendsStories

    override fun onCreateViewHolder(parent: ViewGroup) =
            StoriesViewHolder(UiUtils.inflate(R.layout.item_stories, parent))

    override fun onBindViewHolder(
            holder: StoriesViewHolder,
            item: StoriesItem.FriendsStories,
            adapterPosition: Int,
            collectionPosition: Int,
            payloads: MutableList<Any>
    ) = holder.bind(item)

}
