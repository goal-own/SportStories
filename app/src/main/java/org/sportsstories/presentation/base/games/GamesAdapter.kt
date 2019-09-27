package org.sportsstories.presentation.base.games

import androidx.recyclerview.widget.DiffUtil
import org.sportsstories.domain.model.Game
import ru.touchin.adapters.DelegationListAdapter

class GamesAdapter : DelegationListAdapter<Game>(CALLBACK) {

    companion object {
        private val CALLBACK = object : DiffUtil.ItemCallback<Game>() {
            override fun areItemsTheSame(oldItem: Game, newItem: Game) =
                    oldItem.firstTeam == newItem.firstTeam && oldItem.secondTeam == newItem.secondTeam

            override fun areContentsTheSame(oldItem: Game, newItem: Game) =
                    oldItem.firstTeam == newItem.firstTeam && oldItem.secondTeam == newItem.secondTeam
        }
    }

    init {
        addDelegate(GamesDelegate())
    }

}
