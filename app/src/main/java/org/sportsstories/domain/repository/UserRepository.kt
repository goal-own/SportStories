package org.sportsstories.domain.repository

import org.sportsstories.data.api.BaseApi
import org.sportsstories.data.storage.DataStorage
import java.util.UUID
import javax.inject.Inject

class UserRepository @Inject constructor(
        private val baseApi: BaseApi,
        private val storage: DataStorage
) {

    // TODO replace with normal userId
    suspend fun getSessionIdAsync(userId: Int, accessToken: String): UUID =
            baseApi.login(accessToken, 123123).data.sessionId

    fun saveSessionId(sessionId: UUID) = storage.storeSessionId(sessionId)

    fun withSession(body: (sessionId: UUID?) -> Unit) {
        body.invoke(loadSessionId())
    }

    private fun loadSessionId() = storage.loadSessionId()


}
