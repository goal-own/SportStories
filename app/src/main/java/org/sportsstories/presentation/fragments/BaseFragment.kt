package org.sportsstories.presentation.fragments

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import org.sportsstories.R

abstract class BaseFragment(
        @LayoutRes private val layoutRes: Int
) : Fragment() {

    companion object {
        private const val STATE_KEY = "STATE_KEY"
    }

    private var state: Parcelable? = null

    open fun alsoOnCreateView(view: View) = Unit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        state = savedInstanceState?.getParcelable(STATE_KEY)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? = inflater.inflate(layoutRes, container, false).also {
        alsoOnCreateView(it)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(STATE_KEY, state)
    }

    protected fun saveState(state: Parcelable) {
        this.state = state
    }

    protected fun <T : Parcelable> restoreState(restorer: (T) -> Unit) {
        getState<T>()?.let(restorer)
    }

    protected fun showErrorSnackbar(
            message: String,
            view: View? = activity?.findViewById(android.R.id.content),
            length: Int = Snackbar.LENGTH_SHORT,
            action: (() -> Unit)? = null,
            actionText: String? = null
    ) = snackbar(view, message, R.color.C3, R.color.C0, length, action, actionText)?.show()

    protected fun showInfoSnackbar(
            message: String,
            view: View? = activity?.findViewById(android.R.id.content),
            length: Int = Snackbar.LENGTH_SHORT,
            action: (() -> Unit)? = null,
            actionText: String? = null
    ) = snackbar(view, message, R.color.C4, R.color.C0, length, action, actionText)?.show()

    @Suppress("UNCHECKED_CAST")
    private fun <T : Parcelable> getState(): T? = state as T?

    private fun snackbar(
            view: View? = activity?.findViewById(android.R.id.content),
            message: String,
            @ColorRes backgroundColor: Int,
            @ColorRes textColor: Int,
            length: Int = Snackbar.LENGTH_SHORT,
            action: (() -> Unit)? = null,
            actionText: String? = null,
            textMaxLines: Int = 4
    ): Snackbar? {
        if (view == null) return null

        val snackbar = Snackbar.make(view, message, length)

        if (action != null && actionText != null) {
            snackbar.setAction(actionText) { action.invoke() }
        }

        snackbar.view.apply {
            setBackgroundColor(ContextCompat.getColor(context, backgroundColor))
            findViewById<TextView>(R.id.snackbar_text).let {
                it.setTextColor(ContextCompat.getColor(context, textColor))
                it.maxLines = textMaxLines
            }
        }

        return snackbar
    }

}
