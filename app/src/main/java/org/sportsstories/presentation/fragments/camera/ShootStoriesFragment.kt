package org.sportsstories.presentation.fragments.camera

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Parcelable
import android.util.Rational
import android.util.Size
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.camera.core.CameraX
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureConfig
import androidx.camera.core.Preview
import androidx.camera.core.PreviewConfig
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getColor
import androidx.core.view.isGone
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
import org.sportsstories.presentation.fragments.BaseFragment
import org.sportsstories.viewmodel.ShootStoriesViewModel
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.OnNeverAskAgain
import permissions.dispatcher.RuntimePermissions
import ru.touchin.extensions.setOnRippleClickListener
import ru.touchin.roboswag.components.utils.UiUtils
import java.io.File

@RuntimePermissions
class ShootStoriesFragment : BaseFragment(), NewsBottomSheet.NewsChooseListener, GamesBottomSheet.GameChooseListener {

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

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_shoot_strories, container, false).also {
        it.post(::initCamera)
        activity?.window?.statusBarColor = ContextCompat.getColor(it.context, R.color.C8)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
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

    override fun onNewsChoosed(news: NewsPreview) {
        with(fragment_shoot_stories_news) {
            isVisible = true
            item_news_preview_time.text = news.date.toTimeString(context)
            item_news_preview_title.text = news.title
            item_news_preview_reactions_count.text = news.reactionsCount.toString()
        }
    }

    override fun onGameChoosed(game: Game) {
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

    @NeedsPermission(Manifest.permission.CAMERA)
    fun openCameraInternal() {
        val rational = Rational(
                fragment_shoot_stories_view_finder.width,
                fragment_shoot_stories_view_finder.height
        )
        val imageCaptureConfig = ImageCaptureConfig.Builder()
                .apply {
                    setTargetAspectRatio(rational)
                    setCaptureMode(ImageCapture.CaptureMode.MIN_LATENCY)
                }.build()
        val imageCapture = ImageCapture(imageCaptureConfig)
        val previewConfig = PreviewConfig.Builder().apply {
            setTargetAspectRatio(rational)
            setTargetResolution(
                    Size(
                            fragment_shoot_stories_view_finder.width,
                            fragment_shoot_stories_view_finder.height
                    )
            )
        }.build()
        val preview = Preview(previewConfig)
        val parent = fragment_shoot_stories_view_finder.parent as ViewGroup
        preview.setOnPreviewOutputUpdateListener {
            parent.removeView(fragment_shoot_stories_view_finder)
            parent.addView(fragment_shoot_stories_view_finder, 0)
            fragment_shoot_stories_view_finder.surfaceTexture = it.surfaceTexture
        }
        fragment_shoot_stories_button.setOnRippleClickListener {
            setState(State.LOADING)
            viewModel.savePhoto(imageCapture, ::onShootSuccess, ::onShootError)
        }
        setState(State.CAMERA)
        CameraX.bindToLifecycle(this, preview, imageCapture)
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

    private fun initViews() {
        fragment_shoot_stories_close.setOnRippleClickListener {
            if (currentState == State.PICTURE) setState(State.CAMERA)
            else if (currentState == State.CAMERA) viewModel.back()
        }
        fragment_shoot_stories_button_upload_button.setOnRippleClickListener {
            viewModel.sendPhoto()
        }
        fragment_shoot_stories_button_papper.setOnRippleClickListener {
            NewsBottomSheet.show(this)
        }
        fragment_shoot_stories_button_ball.setOnRippleClickListener {
            GamesBottomSheet.show(this)
        }

    }

    private fun initScoreOrStartTime(item: Game, view: View) = with(view) {
        when (item.status) {
            GameStatus.LIVE,
            GameStatus.ENDED -> {
                item_game_view_switcher.showChild(R.id.item_game_score)
                item_game_score.text = getString(
                        R.string.game_scores,
                        item.firstTeamScore ?: 0,
                        item.secondTeamScore ?: 0
                )
                item_game_score.setTextColor(getColor(requireContext(), if (item.status == GameStatus.LIVE) {
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
            setBottomNavigationVisibility(true)
            fragment_shoot_stories_news.isGone = true
            fragment_shoot_stories_game_info.isGone = true
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

    @Parcelize
    private enum class State : Parcelable {
        LOADING, CAMERA, PICTURE
    }

}

