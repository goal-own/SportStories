package org.sportsstories.presentation.fragments.camera

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.dialog_games.gialog_games_recycler
import org.sportsstories.R
import org.sportsstories.domain.model.Game
import org.sportsstories.internal.di.app.viewmodel.LifecycleViewModelProviders
import org.sportsstories.lifecycle.event.ContentEvent
import org.sportsstories.presentation.base.games.GamesAdapter
import org.sportsstories.presentation.decorators.GridMarginDecorations
import org.sportsstories.viewmodel.MainScreenViewModel
import ru.touchin.roboswag.components.utils.UiUtils

class GamesBottomSheet : BottomSheetDialogFragment() {

    private val viewModel by lazy {
        LifecycleViewModelProviders.of(this).get(MainScreenViewModel::class.java)
    }

    private val gamesAdapter = GamesAdapter()

    companion object {

        private const val REQUEST_CODE = 2 // value isn't necessary

        fun show(fragment: Fragment) {
            GamesBottomSheet().also {
                it.setTargetFragment(fragment, REQUEST_CODE)
            }.show(fragment.fragmentManager!!, "GamesBottomSheet")
        }

    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_games, container, false)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        setStyle(STYLE_NO_FRAME, R.style.TransparentBottomSheetDialog)
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchGames()
        initViews()
        initObservers()
    }

    private fun initViews() {
        gialog_games_recycler.adapter = gamesAdapter
        dialog?.setOnShowListener { dialog ->
            val frameLayout =
                    (dialog as BottomSheetDialog).findViewById<FrameLayout>(R.id.design_bottom_sheet)
            BottomSheetBehavior.from(frameLayout).state = BottomSheetBehavior.STATE_EXPANDED
        }
        gialog_games_recycler.addItemDecoration(GridMarginDecorations(UiUtils.OfMetrics.dpToPixels(requireContext(), 8f).toInt()))
        dialog?.window?.decorView?.background = ColorDrawable(Color.TRANSPARENT)
        gamesAdapter.itemClickListener = { item, _ ->
            (item as? Game)?.let {
                (targetFragment as GameChooseListener).onGameChoosed(it)
            }
            dismiss()
        }
    }

    private fun initObservers() {
        viewModel.games.observe(this, Observer { event ->
            when (event) {
                is ContentEvent.Success -> gamesAdapter.submitList(event.data)
            }
        })
    }

    interface GameChooseListener {
        fun onGameChoosed(game: Game)
    }

}
