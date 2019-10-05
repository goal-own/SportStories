package org.sportsstories.presentation.controllers

import android.util.Rational
import android.util.Size
import android.view.TextureView
import android.view.ViewGroup
import androidx.camera.core.CameraX
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureConfig
import androidx.camera.core.Preview
import androidx.camera.core.PreviewConfig
import androidx.lifecycle.LifecycleOwner
import java.io.File

class CameraXController(
        private val lifecycleOwner: LifecycleOwner,
        private val textureView: TextureView
) {

    var onPhotoSaved: (File) -> Unit = {}
    var onPhotoSavedError: () -> Unit = {}

    private val onImageSavedListener = object : ImageCapture.OnImageSavedListener {
        override fun onImageSaved(file: File) {
            onPhotoSaved.invoke(file)
        }

        override fun onError(imageCaptureError: ImageCapture.ImageCaptureError, message: String, cause: Throwable?) {
            onPhotoSavedError.invoke()
        }
    }

    private val rational = Rational(
            textureView.width,
            textureView.height
    )

    private val imageCapture = ImageCaptureConfig
            .Builder()
            .setTargetAspectRatio(rational)
            .setCaptureMode(ImageCapture.CaptureMode.MAX_QUALITY)
            .build().let(::ImageCapture)

    fun bind() {
        val previewConfig = PreviewConfig
                .Builder()
                .setTargetAspectRatio(rational)
                .setTargetResolution(Size(rational.numerator, rational.denominator))
                .build()
        val preview = Preview(previewConfig)
        val parent = textureView.parent as ViewGroup
        preview.setOnPreviewOutputUpdateListener {
            parent.removeView(textureView)
            parent.addView(textureView, 0)
            textureView.surfaceTexture = it.surfaceTexture
        }
        CameraX.bindToLifecycle(lifecycleOwner, preview, imageCapture)
    }

    fun capture(file: File) {
        imageCapture.takePicture(file, onImageSavedListener)
    }

}
