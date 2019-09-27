package org.sportsstories.data.preferences.property

import android.content.SharedPreferences
import org.sportsstories.data.preferences.PreferenceFactory

abstract class Property<T>(val name: String) {

    protected val preferences: SharedPreferences
        get() = PreferenceFactory.preferences

    abstract fun read(default: T? = null): T?

    abstract fun write(value: T?)

    protected fun edit(editor: (SharedPreferences.Editor) -> Unit) {
        val edit = preferences.edit()
        editor.invoke(edit)
        edit.apply()
    }
}