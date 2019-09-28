package org.sportsstories.domain.usecases

import android.graphics.Bitmap
import android.net.Uri
import kotlinx.coroutines.Deferred
import org.sportsstories.domain.data_provider.UriBitmapProcessor
import org.sportsstories.domain.model.camera.LocalAttachment
import org.sportsstories.domain.repository.StoriesRepository
import org.sportsstories.utils.async
import java.io.FileOutputStream
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CameraUseCase @Inject constructor(
        private val uriBitmapProcessor: UriBitmapProcessor,
        private val photoMetaDataUseCase: PhotoMetaDataUseCase,
        private val storiesRepository: StoriesRepository
) {

    companion object {

        internal const val PHOTO_QUALITY = 70

        private const val META_DATE_DIR_NAME = "stories_meta"

    }

    fun uploadPhotoAsync(byteArray: ByteArray): Deferred<Unit> = async {
        storiesRepository.uploadStories(byteArray)
    }

    fun saveImageFromGalleryAsync(uri: Uri): Deferred<LocalAttachment> = async {
        val (bitmap, filePathData) =
                uriBitmapProcessor.getBitmap(uri) to photoMetaDataUseCase.getAndPrepareFilePathData(META_DATE_DIR_NAME)
        FileOutputStream(filePathData.file).use { stream ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, PHOTO_QUALITY, stream)
        }

        toLocalAttachment(filePathData)
    }

    private fun toLocalAttachment(pathData: PhotoMetaDataUseCase.FilePathData): LocalAttachment = LocalAttachment(
            id = pathData.id,
            mimeType = "image/jpeg",
            fileName = pathData.fileName,
            fullPath = pathData.file.absolutePath,
            size = pathData.file.length()
    )

}
