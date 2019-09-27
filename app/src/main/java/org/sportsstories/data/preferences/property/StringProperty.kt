package org.sportsstories.data.preferences.property

class StringProperty(name: String) : Property<String>(name) {

    override fun read(default: String?): String? = preferences.getString(name, default)

    override fun write(value: String?) = edit { it.putString(name, value) }
}
