package org.sportsstories.presentation.fragments.camera.bottom_sheet_controller

import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.bottom_games_sheet.view.bottom_games_sheet_recyclerview
import kotlinx.android.synthetic.main.item_game.view.item_game_score
import kotlinx.android.synthetic.main.item_game.view.item_game_start_time
import kotlinx.android.synthetic.main.item_game.view.item_game_view_switcher
import org.sportsstories.R
import org.sportsstories.domain.model.Game
import org.sportsstories.domain.model.GameStatus
import org.sportsstories.extensions.toDateWithMonthAndTimeString
import org.sportsstories.internal.di.app.viewmodel.LifecycleViewModelProviders
import org.sportsstories.lifecycle.event.ContentEvent
import org.sportsstories.presentation.base.games.GamesAdapter
import org.sportsstories.presentation.controllers.FullScreenBottomSheetController
import org.sportsstories.presentation.decorators.GridMarginDecorations
import org.sportsstories.viewmodel.MainScreenViewModel
import ru.touchin.roboswag.components.utils.UiUtils

class GamesBottomSheetController(
        lifecycleOwner: LifecycleOwner,
        parentContainer: View
) : FullScreenBottomSheetController(parentContainer.findViewById(R.id.bottom_games_sheet)) {

    private val viewModel by lazy {
        LifecycleViewModelProviders.of(lifecycleOwner).get(MainScreenViewModel::class.java)
    }

    var onGameChose: (Game) -> Unit = {}

    private val gamesAdapter = GamesAdapter()

    init {
        bottomSheetContentContainer.bottom_games_sheet_recyclerview.adapter = gamesAdapter
        bottomSheetContentContainer.bottom_games_sheet_recyclerview.addItemDecoration(
                GridMarginDecorations(UiUtils.OfMetrics.dpToPixels(parentContainer.context, 8f).toInt())
        )
        gamesAdapter.itemClickListener = { item, _ ->
            onGameChose(item)
            dismiss()
        }
        viewModel.games.observe(lifecycleOwner, Observer { event ->
            when (event) {
                is ContentEvent.Success -> gamesAdapter.submitList(event.data)
            }
        })
        viewModel.fetchGames()
    }

}
