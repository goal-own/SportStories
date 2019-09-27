package org.sportsstories.extensions

import android.view.View
import androidx.core.view.isGone
import androidx.core.view.isVisible

fun View.makeVisibleOrGone(value: Boolean) {
    isVisible = value
    isGone = !value
}
