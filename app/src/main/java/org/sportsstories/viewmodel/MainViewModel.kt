package org.sportsstories.viewmodel

import android.graphics.Bitmap
import android.net.Uri
import android.os.Handler
import androidx.lifecycle.MutableLiveData
import org.sportsstories.domain.model.VkUser
import org.sportsstories.domain.model.camera.LocalAttachment
import org.sportsstories.domain.usecases.CameraUseCase
import org.sportsstories.domain.usecases.UserUserCase
import org.sportsstories.extensions.toByteArray
import org.sportsstories.internal.routing.navigation.root.RootScreenNavigation
import org.sportsstories.lifecycle.event.ContentEvent
import org.sportsstories.lifecycle_coroutines.CoroutinesViewModel
import ru.terrakok.cicerone.NavigatorHolder
import java.io.File
import javax.inject.Inject

class MainViewModel @Inject constructor(
        val navigatorHolder: NavigatorHolder,
        private val rootScreenNavigation: RootScreenNavigation,
        private val userUserCase: UserUserCase,
        private val cameraUseCase: CameraUseCase
) : CoroutinesViewModel() {

    val loginEvent = MutableLiveData<ContentEvent<Unit>>()

    val photo = MutableLiveData<ContentEvent<Bitmap>>()

    val fileChoosenEvent = MutableLiveData<ContentEvent<LocalAttachment>>()

    fun onLogin(user: VkUser) = userUserCase
            .authorize(user)
            .dispatchTo(loginEvent)

    fun openSplashThenStartScreens() {
        rootScreenNavigation.start.openSplash()
        Handler().postDelayed(rootScreenNavigation.start::openLoginFragmentAsRoot, 1000)
    }

    fun sendPhoto(fullPath: String) {
        // TODO remove method here
        cameraUseCase.uploadPhotoAsync(File(fullPath).toByteArray())
    }

    fun onFileChoosen(uri: Uri) {
        cameraUseCase
                .saveImageFromGalleryAsync(uri)
                .dispatchTo(fileChoosenEvent)
    }

}
