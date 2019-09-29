package org.sportsstories.viewmodel

import androidx.camera.core.ImageCapture
import androidx.lifecycle.MutableLiveData
import org.sportsstories.domain.usecases.CameraUseCase
import org.sportsstories.domain.usecases.PhotoMetaDataUseCase
import org.sportsstories.internal.routing.navigation.root.RootScreenNavigation
import org.sportsstories.lifecycle.event.ContentEvent
import org.sportsstories.lifecycle_coroutines.CoroutinesViewModel
import java.io.File
import javax.inject.Inject

class ShootStoriesViewModel @Inject constructor(
        private val rootScreenNavigation: RootScreenNavigation,
        private val photoMetaDataUseCase: PhotoMetaDataUseCase,
        private val cameraUseCase: CameraUseCase
) : CoroutinesViewModel() {

    companion object {

        private const val STORIES_DIR = "stories"

    }

    var savedFile: File? = null

    val uploadEvent = MutableLiveData<ContentEvent<Unit>>()

    fun savePhoto(imageCapture: ImageCapture, onSuccess: (File) -> Unit, onError: () -> Unit) {
        photoMetaDataUseCase.getAndPrepareFilePathData(STORIES_DIR)
        val file = photoMetaDataUseCase.getAndPrepareFilePathData(STORIES_DIR).file
        imageCapture.takePicture(file, OnImageSavedListener(onSuccess, onError))
    }

    fun sendPhoto() {
        savedFile?.let { file ->
            cameraUseCase
                    .uploadPhotoAsync(file.readBytes())
                    .dispatchTo(uploadEvent)
        }
    }

    fun back() {
        rootScreenNavigation.back()
    }

    private inner class OnImageSavedListener(
            private val onSuccess: (File) -> Unit,
            private val onError: () -> Unit
    ) : ImageCapture.OnImageSavedListener {
        override fun onImageSaved(file: File) {
            savedFile = file
            onSuccess.invoke(file)
        }

        override fun onError(
                imageCaptureError: ImageCapture.ImageCaptureError,
                message: String,
                cause: Throwable?
        ) {
            onError.invoke()
        }
    }

}
