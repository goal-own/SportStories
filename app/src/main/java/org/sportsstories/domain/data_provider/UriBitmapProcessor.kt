package org.sportsstories.domain.data_provider

import android.graphics.Bitmap
import android.net.Uri

interface UriBitmapProcessor {
    fun getBitmap(uri: Uri): Bitmap
    fun getBitmap(byteArray: ByteArray): Bitmap
}
