package org.sportsstories.domain.data_provider.impl

import android.content.Context
import org.sportsstories.domain.data_provider.ResourceManager
import java.io.File
import javax.inject.Inject

class ResourceManagerImpl @Inject constructor(
        private val context: Context
) : ResourceManager {

    override fun getInternalFileStoragePath(): File = context.filesDir

}
