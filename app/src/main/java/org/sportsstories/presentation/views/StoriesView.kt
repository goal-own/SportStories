package org.sportsstories.presentation.views

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewTreeObserver
import androidx.cardview.widget.CardView
import androidx.core.view.doOnNextLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomViewTarget
import com.bumptech.glide.request.transition.Transition
import kotlinx.android.synthetic.main.view_stories.view.item_stories_background
import kotlinx.android.synthetic.main.view_stories.view.item_stories_blur
import kotlinx.android.synthetic.main.view_stories.view.item_stories_text
import org.sportsstories.R
import org.sportsstories.utils.ui.BlurBuilder
import ru.touchin.roboswag.components.utils.UiUtils

class StoriesView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {

    init {
        LayoutInflater.from(context).inflate(R.layout.view_stories, this, true)
        elevation = UiUtils.OfMetrics.dpToPixels(context, 4f)
        radius = UiUtils.OfMetrics.dpToPixels(context, 6f)
    }

    private val blurPreDrawListener = object : ViewTreeObserver.OnPreDrawListener {
        override fun onPreDraw() = if (!isInEditMode) {
            viewTreeObserver.removeOnPreDrawListener(this)
            updateBlur()
            true
        } else {
            false
        }
    }

    fun bind(urlImage: String, name: String) {
        clipToOutline = true

        item_stories_text.text = name

        Glide.with(this)
                .asDrawable()
                .load(urlImage)
                .centerCrop()
                .into(object : CustomViewTarget<StoriesView, Drawable>(this) {
                    override fun onLoadFailed(errorDrawable: Drawable?) {
                        setBackground(errorDrawable, false)
                    }

                    override fun onResourceCleared(placeholder: Drawable?) {
                        setBackground(placeholder, false)
                    }

                    override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                        if (item_stories_background.measuredWidth == 0 || item_stories_blur.measuredHeight == 0) {
                            doOnNextLayout { setBackground(resource, true) }
                        } else {
                            setBackground(resource, true)
                        }
                    }
                })
    }

    private fun setBackground(background: Drawable?, updateBlur: Boolean) {
        when {
            isInEditMode -> return
            updateBlur -> viewTreeObserver.addOnPreDrawListener(blurPreDrawListener)
        }
        item_stories_background.setImageDrawable(background)
    }

    private fun updateBlur() {
        val internalBitmap = Bitmap.createBitmap(
                item_stories_background.measuredWidth,
                item_stories_blur.measuredHeight,
                Bitmap.Config.ARGB_8888
        )
        val internalCanvas = Canvas(internalBitmap)

        setupInternalCanvasMatrix(internalCanvas)
        item_stories_background.draw(internalCanvas)
        item_stories_blur.setImageBitmap(BlurBuilder.blur(context, internalBitmap))
    }

    private fun setupInternalCanvasMatrix(canvas: Canvas) {
        val translationY = item_stories_background.measuredHeight - item_stories_blur.measuredHeight
        canvas.translate(0F, -translationY.toFloat())
    }

}
