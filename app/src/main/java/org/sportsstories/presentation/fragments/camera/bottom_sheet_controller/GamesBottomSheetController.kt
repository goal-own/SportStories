package org.sportsstories.presentation.fragments.camera.bottom_sheet_controller

import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.bottom_games_sheet.view.bottom_games_sheet_recyclerview
import kotlinx.android.synthetic.main.fragment_shoot_strories.view.fragment_shoot_stories_game_info
import kotlinx.android.synthetic.main.item_game.view.item_game_first_team_country
import kotlinx.android.synthetic.main.item_game.view.item_game_first_team_logo
import kotlinx.android.synthetic.main.item_game.view.item_game_score
import kotlinx.android.synthetic.main.item_game.view.item_game_second_team_country
import kotlinx.android.synthetic.main.item_game.view.item_game_second_team_logo
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

    private fun onGameChose(game: Game) {
        with(bottomSheetContentContainer.fragment_shoot_stories_game_info) {
            isVisible = true
            com.bumptech.glide.Glide.with(this)
                    .load(game.firstTeam.logoUrl)
                    .centerCrop()
                    .placeholder(R.drawable.bg_action_button)
                    .into(item_game_first_team_logo)
            com.bumptech.glide.Glide.with(this)
                    .load(game.secondTeam.logoUrl)
                    .centerCrop()
                    .placeholder(R.drawable.bg_action_button)
                    .into(item_game_second_team_logo)
            item_game_first_team_country.text = game.firstTeam.country
            item_game_second_team_country.text = game.secondTeam.country
            initScoreOrStartTime(game, this)
        }
    }

    private fun initScoreOrStartTime(item: Game, view: View) = with(view) {
        when (item.status) {
            GameStatus.LIVE,
            GameStatus.ENDED -> {
                item_game_view_switcher.showChild(R.id.item_game_score)
                item_game_score.text = view.context.getString(
                        R.string.game_scores,
                        item.firstTeamScore ?: 0,
                        item.secondTeamScore ?: 0
                )
                item_game_score.setTextColor(
                        ContextCompat.getColor(view.context, if (item.status == GameStatus.LIVE) R.color.C6 else R.color.C5)
                )
            }
            GameStatus.NOT_STARTED -> {
                item_game_view_switcher.showChild(R.id.item_game_start_time)
                item_game_start_time.text = item.startTime.toDateWithMonthAndTimeString(context)
            }
        }
    }

}
