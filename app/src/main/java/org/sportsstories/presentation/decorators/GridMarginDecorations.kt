package org.sportsstories.presentation.decorators

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class GridMarginDecorations(
        private val offset: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildLayoutPosition(view)
        if ((position + 1) % 4 in listOf(1, 2)) {
            outRect.left = offset * 2
            outRect.right = offset
        } else {
            outRect.right = offset * 2
            outRect.left = offset
        }
        outRect.top = offset
        outRect.bottom = offset
    }

}
