package org.sportsstories.domain.data_provider.impl

import org.sportsstories.domain.data_provider.PhotoFileResourceManager
import org.sportsstories.domain.data_provider.ResourceManager
import java.io.File
import javax.inject.Inject

class PhotoFileResourceManagerImpl @Inject constructor(
        private val resourceManager: ResourceManager
) : PhotoFileResourceManager {

    companion object {

        private const val STORIES_DIR = "stories"

    }

    override fun getPhotosDirectoryById(id: String): File {
        return File(filesDir, id)
    }

    override fun cleanDir(path: String) {
        File(path).deleteRecursively()
    }

    private val filesDir: File
        get() = File(resourceManager.getInternalFileStoragePath(), STORIES_DIR)

}
