package org.sportsstories.domain.usecases

import com.squareup.moshi.Moshi
import org.joda.time.DateTime
import org.sportsstories.domain.data_provider.IdentityGenerator
import org.sportsstories.domain.data_provider.PhotoFileResourceManager
import java.io.File
import javax.inject.Inject

class PhotoMetaDataUseCase @Inject constructor(
        private val moshi: Moshi,
        private val photoFileResourceManager: PhotoFileResourceManager,
        private val identityGenerator: IdentityGenerator
) {

    companion object {
        private const val fileExtension = "jpg"
        private const val elsePhotosPath = "else"
    }

    fun getAndPrepareFilePathData(directoryName: String, hidden: Boolean = false): FilePathData {
        val id = identityGenerator.generateUniqueString()
        val fileName = "$id.$fileExtension"
        val relativePath = if (hidden) "$elsePhotosPath/$fileName" else fileName
        val dataDir = photoFileResourceManager.getPhotosDirectoryById(directoryName)
        val filePathData = FilePathData(id, File(dataDir, relativePath), relativePath, fileName)
        prepareFile(filePathData.file)
        return filePathData
    }

    fun getMetaData(
            photo: File
    ): PhotoMetaDataResult {
        val metaData = PhotoMetaData(
                date = DateTime.now(),
                fileName = photo.name
        )

        val metaDataJson = moshi.adapter(PhotoMetaData::class.java).toJson(metaData)

        return PhotoMetaDataResult(metaData, metaDataJson)
    }

    private fun prepareFile(file: File) {
        if (file.exists()) {
            file.delete()
        }
        file.parentFile.mkdirs()
    }

    data class FilePathData(val id: String, val file: File, val relativePath: String, val fileName: String)

    data class PhotoMetaDataResult(val data: PhotoMetaData, val dataAsJson: String)

    data class PhotoMetaData(
            val date: DateTime,
            val fileName: String
    )

}
