package org.sportsstories.domain.data_provider

import android.net.Uri
import java.io.InputStream

interface UriFileProcessor {

    fun getUriFileInputStream(uri: Uri): InputStream?

    fun getMimeType(uri: Uri): String?

    fun getFileNameAndSize(uri: Uri): Pair<String, Long>?

}
