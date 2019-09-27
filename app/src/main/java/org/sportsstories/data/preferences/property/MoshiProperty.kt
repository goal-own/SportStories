package org.sportsstories.data.preferences.property

import com.squareup.moshi.Moshi
import org.sportsstories.data.preferences.PreferenceFactory

class MoshiProperty<T>(
        name: String,
        private val clazz: Class<T>
) : Property<T>(name) {

    private val innerProperty = StringProperty(name)

    companion object {
        private val MOSHI: Moshi by lazy { PreferenceFactory.moshi }
    }

    override fun read(default: T?): T? {
        val serialized = innerProperty.read()
        if (serialized == null) {
            return default
        } else {
            return MOSHI.adapter(clazz).fromJson(serialized)
        }
    }

    override fun write(value: T?) {
        if (value != null) {
            innerProperty.write(MOSHI.adapter(clazz).toJson(value))
        } else {
            innerProperty.write(null)
        }
    }

}