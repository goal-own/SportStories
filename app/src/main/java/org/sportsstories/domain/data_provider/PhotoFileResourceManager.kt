package org.sportsstories.domain.data_provider

import java.io.File

interface PhotoFileResourceManager {

    fun getPhotosDirectoryById(id: String): File
    fun cleanDir(path: String)

}
