package org.sportsstories.data.storage

import java.util.UUID

interface DataStorage {

    fun storeSessionId(sessionId: UUID)
    fun loadSessionId(): UUID?

}
