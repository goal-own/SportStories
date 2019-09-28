package org.sportsstories.domain.data_provider.impl

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import org.sportsstories.domain.data_provider.UriBitmapProcessor
import javax.inject.Inject

class UriBitmapProcessorImpl @Inject constructor(
        private val context: Context
) : UriBitmapProcessor {

    override fun getBitmap(uri: Uri): Bitmap {
        return context.contentResolver.openInputStream(uri).use {
            BitmapFactory.decodeStream(it)
        }
    }

    override fun getBitmap(byteArray: ByteArray): Bitmap {
        return BitmapFactory.decodeStream(byteArray.inputStream())
    }

}
