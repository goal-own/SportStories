package org.sportsstories.presentation.fragments.login

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.vk.api.sdk.VK
import kotlinx.android.synthetic.main.fragment_login.fragment_login_button
import kotlinx.android.synthetic.main.fragment_login.fragment_login_logo
import org.sportsstories.R
import org.sportsstories.extensions.canLaunchIntent
import org.sportsstories.internal.di.app.viewmodel.LifecycleViewModelProviders
import org.sportsstories.lifecycle.event.ContentEvent
import org.sportsstories.presentation.fragments.BaseFragment
import org.sportsstories.utils.SequenceGenerator
import org.sportsstories.viewmodel.LoginViewModel
import org.sportsstories.viewmodel.MainViewModel
import permissions.dispatcher.NeedsPermission
import ru.touchin.extensions.setOnRippleClickListener

class LoginFragment : BaseFragment() {

    companion object {

        fun newInstance() = LoginFragment()

        private val IMAGE_PICK_REQUEST_CODE = SequenceGenerator.nextInt()
        private val TAKE_PHOTO_REQUEST_CODE = SequenceGenerator.nextInt()

        private val DIALOG_ID = SequenceGenerator.nextInt()

    }

    private val viewModel by lazy {
        LifecycleViewModelProviders.of(this).get(MainViewModel::class.java)
    }

    private val loginViewModel by lazy {
        LifecycleViewModelProviders.of(this).get(LoginViewModel::class.java)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_login, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObservers()
    }

    private fun initViews() {
        fragment_login_button.setOnRippleClickListener {
            VK.login(requireActivity())
        }
    }

    private fun initObservers() {
        loginViewModel.loginEvent.observe(this, Observer { event ->
            when (event) {
                is ContentEvent.Success -> loginViewModel.openMainScreen()
                is ContentEvent.Error -> Toast.makeText(activity, "Can't log in", Toast.LENGTH_LONG).show()
            }
        })
        // TODO MOVE IT TO STORIES SHOT FRAGMENT
        viewModel.fileChoosenEvent.observe(this, Observer { event ->
            when (event) {
                is ContentEvent.Success -> viewModel.sendPhoto(event.data.fullPath)
                is ContentEvent.Error -> Toast.makeText(context, event.throwable.message, Toast.LENGTH_LONG).show()
            }
        })
        // TODO MOVE IT TO STORIES SHOT FRAGMENT
        viewModel.photo.observe(this, Observer { event ->
            when (event) {
                is ContentEvent.Success -> Glide.with(this)
                        .asBitmap()
                        .load(event.data)
                        .into(fragment_login_logo)
            }
        })
    }

    // TODO MOVE IT TO STORIES SHOT FRAGMENT
    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                IMAGE_PICK_REQUEST_CODE -> intent?.data?.let(viewModel::onFileChoosen)
                // TAKE_PHOTO_REQUEST_CODE -> viewModel()
            }
        }
    }

    // TODO MOVE IT TO STORIES SHOT FRAGMENT
    @NeedsPermission(Manifest.permission.CAMERA)
    fun openCameraInternal(intent: Intent) {
        if (activity?.canLaunchIntent(intent) == true) {
            startActivityForResult(intent, TAKE_PHOTO_REQUEST_CODE)
        }
    }

    // TODO MOVE IT TO STORIES SHOT FRAGMENT
    private fun openGallery() {
        startActivityForResult(
                Intent.createChooser(
                        getFileChooserIntent(),
                        context?.getString(R.string.choose_photo_from_gallery_title)
                ),
                IMAGE_PICK_REQUEST_CODE
        )
    }

    // TODO MOVE IT TO STORIES SHOT FRAGMENT
    private fun getFileChooserIntent() =
            Intent(Intent.ACTION_GET_CONTENT)
                    .apply {
                        addCategory(Intent.CATEGORY_OPENABLE)
                        type = "*/*"
                        putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("image/jpeg", "image/png"))
                        putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                    }

}
