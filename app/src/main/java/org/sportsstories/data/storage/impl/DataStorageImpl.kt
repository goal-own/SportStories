package org.sportsstories.data.storage.impl

import android.content.Context
import com.squareup.moshi.Moshi
import org.sportsstories.data.preferences.PreferenceFactory
import org.sportsstories.data.preferences.entities.SessionId
import org.sportsstories.data.preferences.property.MoshiProperty
import org.sportsstories.data.storage.DataStorage
import java.util.UUID
import java.util.concurrent.locks.ReentrantReadWriteLock
import javax.inject.Inject
import kotlin.concurrent.read
import kotlin.concurrent.write

class DataStorageImpl @Inject constructor(
        moshi: Moshi,
        context: Context
) : DataStorage {

    private val lock = ReentrantReadWriteLock()

    private val sessionProperty = MoshiProperty("sessionId", SessionId::class.java)

    init {
        PreferenceFactory.init(context, moshi)
    }

    override fun storeSessionId(sessionId: UUID) {
        lock.write {
            sessionProperty.write(SessionId((sessionId.toString())))
        }
    }

    override fun loadSessionId(): UUID? = lock.read {
        sessionProperty.read()?.id?.let { UUID.fromString(it) }
    }

}
