package org.sportsstories.presentation.fragments.camera

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.core.content.ContextCompat.getColor
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.fragment_shoot_strories.fragment_shoot_stories_button
import kotlinx.android.synthetic.main.fragment_shoot_strories.fragment_shoot_stories_button_ball
import kotlinx.android.synthetic.main.fragment_shoot_strories.fragment_shoot_stories_button_container
import kotlinx.android.synthetic.main.fragment_shoot_strories.fragment_shoot_stories_button_papper
import kotlinx.android.synthetic.main.fragment_shoot_strories.fragment_shoot_stories_button_upload_button
import kotlinx.android.synthetic.main.fragment_shoot_strories.fragment_shoot_stories_button_upload_container
import kotlinx.android.synthetic.main.fragment_shoot_strories.fragment_shoot_stories_close
import kotlinx.android.synthetic.main.fragment_shoot_strories.fragment_shoot_stories_game_info
import kotlinx.android.synthetic.main.fragment_shoot_strories.fragment_shoot_stories_loading_bar
import kotlinx.android.synthetic.main.fragment_shoot_strories.fragment_shoot_stories_news
import kotlinx.android.synthetic.main.fragment_shoot_strories.fragment_shoot_stories_picture
import kotlinx.android.synthetic.main.fragment_shoot_strories.fragment_shoot_stories_view_finder
import kotlinx.android.synthetic.main.item_game.view.item_game_first_team_country
import kotlinx.android.synthetic.main.item_game.view.item_game_first_team_logo
import kotlinx.android.synthetic.main.item_game.view.item_game_score
import kotlinx.android.synthetic.main.item_game.view.item_game_second_team_country
import kotlinx.android.synthetic.main.item_game.view.item_game_second_team_logo
import kotlinx.android.synthetic.main.item_game.view.item_game_start_time
import kotlinx.android.synthetic.main.item_game.view.item_game_view_switcher
import kotlinx.android.synthetic.main.item_news_preview.view.item_news_preview_reactions_count
import kotlinx.android.synthetic.main.item_news_preview.view.item_news_preview_time
import kotlinx.android.synthetic.main.item_news_preview.view.item_news_preview_title
import org.sportsstories.R
import org.sportsstories.domain.model.Game
import org.sportsstories.domain.model.GameStatus
import org.sportsstories.domain.model.NewsPreview
import org.sportsstories.extensions.toDateWithMonthAndTimeString
import org.sportsstories.extensions.toTimeString
import org.sportsstories.internal.di.app.viewmodel.LifecycleViewModelProviders
import org.sportsstories.lifecycle.event.ContentEvent
import org.sportsstories.presentation.controllers.CameraXController
import org.sportsstories.presentation.fragments.BaseFragment
import org.sportsstories.presentation.fragments.camera.bottom_sheet_controller.GamesBottomSheetController
import org.sportsstories.presentation.fragments.camera.bottom_sheet_controller.NewsBottomSheetController
import org.sportsstories.viewmodel.ShootStoriesViewModel
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.OnNeverAskAgain
import permissions.dispatcher.RuntimePermissions
import ru.touchin.extensions.setOnRippleClickListener
import ru.touchin.roboswag.components.utils.UiUtils
import java.io.File

@RuntimePermissions
class ShootStoriesFragment : BaseFragment(
        R.layout.fragment_shoot_strories
) {

    companion object {

        fun newInstance() = ShootStoriesFragment()

    }

    private var currentState = State.LOADING

    private val viewModel by lazy {
        LifecycleViewModelProviders.of(this).get(ShootStoriesViewModel::class.java)
    }

    private val navigationTranslationY = 0F
    private val shownNavigationTranslationY by lazy(LazyThreadSafetyMode.NONE) {
        UiUtils.OfMetrics.dpToPixels(requireContext(), 130f)
    }

    private lateinit var newsBottomSheetController: NewsBottomSheetController
    private lateinit var gamesBottomSheetController: GamesBottomSheetController
    private lateinit var cameraXController: CameraXController

    override fun alsoOnCreateView(view: View) {
        activity?.window?.statusBarColor = getColor(view.context, R.color.C8)
        view.post(::initCamera)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        initControllers(view)
        initViews()
    }

    @SuppressLint("NeedOnRequestPermissionsResult")
    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult(requestCode, grantResults)
    }

    override fun onPause() {
        super.onPause()
        saveState(currentState)
    }

    override fun onResume() {
        super.onResume()
        restoreState(::currentState::set)
    }

    @NeedsPermission(Manifest.permission.CAMERA)
    fun openCameraInternal() {
        cameraXController.bind()
        setState(State.CAMERA)
        cameraXController.onPhotoSaved = ::onShootSuccess
        cameraXController.onPhotoSavedError = ::onShootError
        fragment_shoot_stories_button.setOnRippleClickListener {
            setState(State.LOADING)
            cameraXController.capture(viewModel.getFileToSaveImage())
        }
    }

    private fun initObservers() {
        viewModel.uploadEvent.observe(this, Observer { event ->
            when (event) {
                is ContentEvent.Success -> {
                    showInfoSnackbar("Фото успешно загружено")
                    viewModel.back()
                }
                is ContentEvent.Error -> {
                    showInfoSnackbar("Не удалось загрузить фото")
                }
            }
        })
    }

    private fun initCamera() {
        openCameraInternalWithPermissionCheck()
    }

    private fun initControllers(view: View) {
        newsBottomSheetController = NewsBottomSheetController(this, view)
        newsBottomSheetController.onNewsChose = ::onNewsChose
        gamesBottomSheetController = GamesBottomSheetController(this, view)
        gamesBottomSheetController.onGameChose = ::onGameChose
        cameraXController = CameraXController(this, fragment_shoot_stories_view_finder)
    }

    private fun initViews() {
        fragment_shoot_stories_close.setOnRippleClickListener {
            if (currentState == State.PICTURE) setState(State.CAMERA)
            else if (currentState == State.CAMERA) viewModel.back()
        }
        fragment_shoot_stories_button_upload_button.setOnRippleClickListener {
            viewModel.sendPhoto()
        }
        fragment_shoot_stories_button_papper.setOnRippleClickListener {
            gamesBottomSheetController.dismiss()
            newsBottomSheetController.show()
        }
        fragment_shoot_stories_button_ball.setOnRippleClickListener {
            newsBottomSheetController.dismiss()
            gamesBottomSheetController.show()
        }

    }

    private fun onShootSuccess(file: File) {
        setState(State.PICTURE)
        Glide.with(this)
                .load(file)
                .into(fragment_shoot_stories_picture)
    }

    private fun onShootError() {
        setState(State.CAMERA)
    }

    @OnNeverAskAgain
    fun onNewerAskAgain() {
        // TODO show dialog
    }

    private fun setState(state: State) {
        currentState = state
        fragment_shoot_stories_picture.isVisible = state == State.PICTURE
        fragment_shoot_stories_loading_bar.isVisible = state == State.LOADING
        fragment_shoot_stories_view_finder.isVisible = state == State.CAMERA
        if (state == State.CAMERA) {
            gamesBottomSheetController.dismissIfVisible()
            newsBottomSheetController.dismissIfVisible()
            setBottomNavigationVisibility(true)
            fragment_shoot_stories_news.isVisible = false
            fragment_shoot_stories_game_info.isVisible = false
        } else if (state == State.PICTURE) {
            setBottomNavigationVisibility(false)
        }
    }

    private fun setBottomNavigationVisibility(visible: Boolean) {
        val desiredTranslation =
                if (visible) navigationTranslationY else shownNavigationTranslationY
        if (desiredTranslation == fragment_shoot_stories_button_container.translationY) return
        fragment_shoot_stories_button_container
                .animate()
                .translationY(desiredTranslation)
                .start()
        val desiredTranslation2 =
                if (visible) shownNavigationTranslationY else navigationTranslationY
        if (desiredTranslation == fragment_shoot_stories_button_container.translationY) return
        fragment_shoot_stories_button_upload_container
                .animate()
                .translationY(desiredTranslation2)
                .start()
    }

    private fun onNewsChose(news: NewsPreview) {
        with(fragment_shoot_stories_news) {
            isVisible = true
            item_news_preview_time.text = news.date.toTimeString(context)
            item_news_preview_title.text = news.title
            item_news_preview_reactions_count.text = news.reactionsCount.toString()
        }
    }

    private fun onGameChose(game: Game) {
        with(fragment_shoot_stories_game_info) {
            isVisible = true
            Glide.with(this)
                    .load(game.firstTeam.logoUrl)
                    .centerCrop()
                    .placeholder(R.drawable.bg_action_button)
                    .into(item_game_first_team_logo)
            Glide.with(this)
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
                        getColor(view.context, if (item.status == GameStatus.LIVE) R.color.C6 else R.color.C5)
                )
            }
            GameStatus.NOT_STARTED -> {
                item_game_view_switcher.showChild(R.id.item_game_start_time)
                item_game_start_time.text = item.startTime.toDateWithMonthAndTimeString(context)
            }
        }
    }

    @Parcelize
    private enum class State : Parcelable {
        LOADING, CAMERA, PICTURE
    }

}

