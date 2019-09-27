package org.sportsstories.presentation.base.games

import android.view.ViewGroup
import org.sportsstories.R
import org.sportsstories.domain.model.Game
import ru.touchin.adapters.ItemAdapterDelegate
import ru.touchin.roboswag.components.utils.UiUtils

class GamesDelegate : ItemAdapterDelegate<GameViewHolder, Game>() {

    override fun onCreateViewHolder(parent: ViewGroup) =
            GameViewHolder(UiUtils.inflate(R.layout.item_game, parent))

    override fun onBindViewHolder(
            holder: GameViewHolder,
            item: Game,
            adapterPosition: Int,
            collectionPosition: Int,
            payloads: MutableList<Any>
    ) = holder.bind(item)
}
