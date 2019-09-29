package org.sportsstories.domain.repository

import org.sportsstories.data.api.BaseApi
import org.sportsstories.data.storage.DataStorage
import org.sportsstories.domain.model.User
import org.sportsstories.utils.async
import java.util.UUID
import javax.inject.Inject

class UserRepository @Inject constructor(
        private val baseApi: BaseApi,
        private val storage: DataStorage
) {

    // TODO replace with normal userId
    suspend fun getSessionIdAsync(userId: Int, accessToken: String): UUID =
            baseApi.login("some access token", 123123).data.sessionId

    suspend fun checkSessionIdAsync(sessionId: UUID): Boolean =
            baseApi.checkSessionId(sessionId.toString()).data

    fun saveUser(user: User) = storage.storeUser(user)

    fun withSession(body: suspend (sessionId: UUID?) -> Unit) = async {
        body.invoke(loadUser()?.sessionId)
    }

    fun loadUser() = storage.loadUser()

}
