package org.sportsstories.domain.data_provider.impl

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import org.sportsstories.domain.data_provider.UriFileProcessor
import java.io.InputStream
import javax.inject.Inject

class UriFileProcessorImpl @Inject constructor(
        private val context: Context
) : UriFileProcessor {

    override fun getUriFileInputStream(uri: Uri): InputStream? =
            context.contentResolver.openInputStream(uri)

    override fun getMimeType(uri: Uri): String? = context.contentResolver.getType(uri)

    override fun getFileNameAndSize(uri: Uri): Pair<String, Long>? =
            context.contentResolver
                    .query(uri, null, null, null, null)
                    ?.use { cursor ->
                        val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                        val sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE)
                        cursor.moveToFirst()
                        cursor.getString(nameIndex) to cursor.getLong(sizeIndex)
                    }

}
