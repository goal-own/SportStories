package org.sportsstories.presentation.base.games

import android.content.Context
import android.graphics.Point
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
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
import ru.touchin.extensions.context
import ru.touchin.extensions.getColor
import ru.touchin.extensions.getString
import ru.touchin.roboswag.components.utils.UiUtils

class GameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    init {
        val size = Point()
        (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay.getSize(size)
        itemView.layoutParams = ViewGroup.LayoutParams(
                ((size.x - UiUtils.OfMetrics.dpToPixels(context, 48f)) / 2).toInt(),
                itemView.layoutParams.height
        )
    }

    fun bind(item: Game) {
        itemView.requestLayout()
        with(itemView) {
            Glide.with(itemView)
                    .load(item.firstTeam.logoUrl)
                    .centerCrop()
                    .placeholder(R.drawable.bg_action_button)
                    .into(item_game_first_team_logo)
            Glide.with(itemView)
                    .load(item.secondTeam.logoUrl)
                    .centerCrop()
                    .placeholder(R.drawable.bg_action_button)
                    .into(item_game_second_team_logo)

            item_game_first_team_country.text = item.firstTeam.country
            item_game_second_team_country.text = item.secondTeam.country
        }
        initScoreOrStartTime(item)
    }

    private fun initScoreOrStartTime(item: Game) = with(itemView) {
        when (item.status) {
            GameStatus.LIVE,
            GameStatus.ENDED -> {
                item_game_view_switcher.showChild(R.id.item_game_score)
                item_game_score.text = getString(
                        R.string.game_scores,
                        item.firstTeamScore ?: 0,
                        item.secondTeamScore ?: 0
                )
                item_game_score.setTextColor(getColor(if (item.status == GameStatus.LIVE) {
                    R.color.C6
                } else {
                    R.color.C5
                }))
            }
            GameStatus.NOT_STARTED -> {
                item_game_view_switcher.showChild(R.id.item_game_start_time)
                item_game_start_time.text = item.startTime.toDateWithMonthAndTimeString(context)
            }
        }
    }

}
