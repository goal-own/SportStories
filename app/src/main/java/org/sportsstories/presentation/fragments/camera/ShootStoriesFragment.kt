package org.sportsstories.presentation.fragments.camera

import android.Manifest
import android.os.Bundle
import android.util.Rational
import android.util.Size
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.camera.core.CameraX
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureConfig
import androidx.camera.core.Preview
import androidx.camera.core.PreviewConfig
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_shoot_strories.fragment_shoot_stories_button
import kotlinx.android.synthetic.main.fragment_shoot_strories.fragment_shoot_stories_loading_bar
import kotlinx.android.synthetic.main.fragment_shoot_strories.fragment_shoot_stories_picture
import kotlinx.android.synthetic.main.fragment_shoot_strories.fragment_shoot_stories_view_finder
import org.sportsstories.R
import org.sportsstories.internal.di.app.viewmodel.LifecycleViewModelProviders
import org.sportsstories.presentation.fragments.BaseFragment
import org.sportsstories.utils.SequenceGenerator
import org.sportsstories.viewmodel.ShootStoriesViewModel
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.OnNeverAskAgain
import permissions.dispatcher.RuntimePermissions
import ru.touchin.extensions.setOnRippleClickListener
import java.io.File

@RuntimePermissions
class ShootStoriesFragment : BaseFragment() {

    companion object {

        fun newInstance() = ShootStoriesFragment()
        private val TAKE_PHOTO_REQUEST_CODE = SequenceGenerator.nextInt()

    }

    private val viewModel by lazy {
        LifecycleViewModelProviders.of(this).get(ShootStoriesViewModel::class.java)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_shoot_strories, container, false).also {
        it.post(::initCamera)
        activity?.window?.statusBarColor = ContextCompat.getColor(it.context, R.color.C8)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult(requestCode, grantResults)
    }

    @NeedsPermission(Manifest.permission.CAMERA)
    fun openCameraInternal() {
        val imageCaptureConfig = ImageCaptureConfig.Builder()
                .apply {
                    setTargetAspectRatio(Rational(fragment_shoot_stories_view_finder.width, fragment_shoot_stories_view_finder.height))
                    setCaptureMode(ImageCapture.CaptureMode.MIN_LATENCY)
                }.build()
        val imageCapture = ImageCapture(imageCaptureConfig)
        val previewConfig = PreviewConfig.Builder().apply {
            setTargetAspectRatio(Rational(fragment_shoot_stories_view_finder.width, fragment_shoot_stories_view_finder.height))
            setTargetResolution(Size(fragment_shoot_stories_view_finder.width, fragment_shoot_stories_view_finder.height))
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

    private fun initCamera() {
        openCameraInternalWithPermissionCheck()
    }

    private fun onShootSuccess(file: File) {
        setState(State.PICTURE)
        Glide.with(this)
                .load(file)
                .into(fragment_shoot_stories_picture)
        Toast.makeText(requireContext(), "Saved", Toast.LENGTH_LONG).show()
    }

    private fun onShootError() {
        Toast.makeText(requireContext(), "Error", Toast.LENGTH_LONG).show()
    }

    @OnNeverAskAgain
    fun onNewerAskAgain() {
        // TODO show dialog
    }

    private fun setState(state: State) {
        fragment_shoot_stories_picture.isVisible = state == State.PICTURE
        fragment_shoot_stories_loading_bar.isVisible = state == State.LOADING
        fragment_shoot_stories_view_finder.isVisible = state == State.CAMERA
    }

    private enum class State {
        LOADING, CAMERA, PICTURE
    }

}

