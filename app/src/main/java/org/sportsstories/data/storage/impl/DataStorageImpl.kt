package org.sportsstories.data.storage.impl

import android.content.Context
import com.squareup.moshi.Moshi
import org.sportsstories.data.preferences.PreferenceFactory
import org.sportsstories.data.preferences.property.MoshiProperty
import org.sportsstories.data.storage.DataStorage
import org.sportsstories.domain.model.User
import java.util.concurrent.locks.ReentrantReadWriteLock
import javax.inject.Inject
import kotlin.concurrent.read
import kotlin.concurrent.write

class DataStorageImpl @Inject constructor(
        moshi: Moshi,
        context: Context
) : DataStorage {

    private val lock = ReentrantReadWriteLock()

    private val userProperty = MoshiProperty("user", User::class.java)

    init {
        PreferenceFactory.init(context, moshi)
    }

    override fun storeUser(user: User) {
        lock.write {
            userProperty.write(user)
        }
    }

    override fun loadUser(): User? = lock.read {
        userProperty.read()
    }

}
