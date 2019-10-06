package org.sportsstories.viewmodel

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

    fun getFileToSaveImage() = photoMetaDataUseCase.getAndPrepareFilePathData(STORIES_DIR).file.also {
        savedFile = it
    }

    fun sendPhoto() {
        savedFile?.let { file ->
            cameraUseCase
                    .uploadPhotoAsync(file)
                    .dispatchTo(uploadEvent)
        }
    }

    fun back() {
        rootScreenNavigation.back()
    }

}
