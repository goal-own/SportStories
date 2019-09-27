package org.sportsstories.utils.ui

import android.content.Context
import android.graphics.Bitmap
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.renderscript.ScriptIntrinsicColorMatrix

object BlurBuilder {

    private const val BLUR_RADIUS = 12F

    fun blur(context: Context, bitmap: Bitmap, radius: Float = BLUR_RADIUS): Bitmap {
        val renderScript = RenderScript.create(context.applicationContext)

        val inputAllocation = Allocation.createFromBitmap(renderScript, bitmap)

        val outputBitmap = Bitmap.createBitmap(bitmap.width, bitmap.height, bitmap.config)

        val outAllocation = Allocation.createTyped(renderScript, inputAllocation.type)

        val blurScript = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript))

        val colorFilterScript = ScriptIntrinsicColorMatrix.create(renderScript)

        blurScript.apply {
            setRadius(radius)
            setInput(inputAllocation)
            forEach(outAllocation)
        }

        colorFilterScript.apply {
            forEach(outAllocation, outAllocation)
        }

        outAllocation.copyTo(outputBitmap)
        inputAllocation.destroy()
        outAllocation.destroy()
        blurScript?.destroy()
        renderScript?.destroy()
        return outputBitmap
    }

}
