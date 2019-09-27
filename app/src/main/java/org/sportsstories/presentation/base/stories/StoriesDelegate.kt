package org.sportsstories.presentation.base.stories

import android.view.ViewGroup
import org.sportsstories.R
import org.sportsstories.domain.model.Stories
import ru.touchin.adapters.ItemAdapterDelegate
import ru.touchin.roboswag.components.utils.UiUtils

class StoriesDelegate : ItemAdapterDelegate<StoriesViewHolder, Stories>() {

    override fun onCreateViewHolder(parent: ViewGroup) =
            StoriesViewHolder(UiUtils.inflate(R.layout.item_stories, parent))

    override fun onBindViewHolder(
            holder: StoriesViewHolder,
            item: Stories,
            adapterPosition: Int,
            collectionPosition: Int,
            payloads: MutableList<Any>
    ) = holder.bind(item)

}
