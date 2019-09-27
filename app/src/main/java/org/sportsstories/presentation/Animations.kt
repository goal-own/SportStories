package org.sportsstories.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.view.animation.AnimationUtils
import org.sportsstories.R

@SuppressLint("StaticFieldLeak")
object Animations {

    lateinit var context: Context

    fun init(context: Context) {
        this.context = context
    }

    val scaleAnimation by lazy { AnimationUtils.loadAnimation(context, R.anim.scale) }

}
