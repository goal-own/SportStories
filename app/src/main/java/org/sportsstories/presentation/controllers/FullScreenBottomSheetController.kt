package org.sportsstories.presentation.controllers

import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.postDelayed
import com.google.android.material.bottomsheet.BottomSheetBehavior
import ru.touchin.roboswag.core.utils.ShouldNotHappenException

abstract class FullScreenBottomSheetController(protected val bottomSheetContentContainer: View) {

    companion object {
        private const val DEFAULT_DELAY = 100L
    }

    private val behavior =
            (bottomSheetContentContainer.layoutParams as CoordinatorLayout.LayoutParams).behavior as BottomSheetBehavior<*>

    private var onShowAction: () -> Unit = {}
    private var onDismissAction: () -> Unit = {}

    init {
        if (behavior.peekHeight > 0) {
            throw ShouldNotHappenException("FullscreenBottomSheet peekHeight should be 0, because STATE_COLLAPSED used to indicate dialog dismiss")
        }

        behavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) = Unit

            override fun onStateChanged(bottomSheet: View, newState: Int) = when (newState) {
                BottomSheetBehavior.STATE_HALF_EXPANDED -> dismiss()
                BottomSheetBehavior.STATE_EXPANDED -> onShowAction.invoke()
                BottomSheetBehavior.STATE_COLLAPSED -> onDismissAction.invoke()
                else -> Unit
            }
        })
    }

    open fun show() {
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    open fun dismiss() {
        behavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    fun dismissIfVisible() {
        if (isVisible()) dismiss()
    }

    open fun dismissWithDelay(delay: Long = DEFAULT_DELAY) {
        bottomSheetContentContainer.postDelayed(delay) {
            dismiss()
        }
    }

    open fun isVisible() = behavior.state == BottomSheetBehavior.STATE_EXPANDED

    open fun setOnShowAction(action: () -> Unit) {
        onShowAction = action
    }

    open fun setOnDismissAction(action: () -> Unit) {
        onDismissAction = action
    }

}
