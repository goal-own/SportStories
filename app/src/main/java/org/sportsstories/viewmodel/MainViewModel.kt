package org.sportsstories.viewmodel

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import org.sportsstories.domain.model.camera.LocalAttachment
import org.sportsstories.domain.usecases.CameraUseCase
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
        private val cameraUseCase: CameraUseCase
) : CoroutinesViewModel() {

    val photo = MutableLiveData<ContentEvent<Bitmap>>()

    val fileChoosenEvent = MutableLiveData<ContentEvent<LocalAttachment>>()

    fun openSplash() {
        rootScreenNavigation.start.openSplash()
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
