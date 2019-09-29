package org.sportsstories.viewmodel

import androidx.camera.core.ImageCapture
import org.sportsstories.domain.usecases.PhotoMetaDataUseCase
import org.sportsstories.lifecycle_coroutines.CoroutinesViewModel
import java.io.File
import javax.inject.Inject

class ShootStoriesViewModel @Inject constructor(
        private val photoMetaDataUseCase: PhotoMetaDataUseCase
) : CoroutinesViewModel() {

    companion object {

        private const val STORIES_DIR = "stories"

    }

    fun savePhoto(imageCapture: ImageCapture, onSuccess: (File) -> Unit, onError: () -> Unit) {
        photoMetaDataUseCase.getAndPrepareFilePathData(STORIES_DIR)
        val file = photoMetaDataUseCase.getAndPrepareFilePathData(STORIES_DIR).file
        imageCapture.takePicture(file, OnImageSavedListener(onSuccess, onError))
    }

    private class OnImageSavedListener(
            private val onSuccess: (File) -> Unit,
            private val onError: () -> Unit
    ) : ImageCapture.OnImageSavedListener {
        override fun onImageSaved(file: File) {
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
