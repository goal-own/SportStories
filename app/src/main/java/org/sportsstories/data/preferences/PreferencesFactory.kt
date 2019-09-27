package org.sportsstories.data.preferences

import android.content.Context
import android.content.SharedPreferences
import com.squareup.moshi.Moshi

object PreferenceFactory {

    internal lateinit var preferences: SharedPreferences
    internal lateinit var moshi: Moshi

    fun init(context: Context, moshi: Moshi) {
        this.moshi = moshi
        preferences = context.getSharedPreferences("preferences", Context.MODE_PRIVATE)
    }

}