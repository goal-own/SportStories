package org.sportsstories.data.storage

import org.sportsstories.domain.model.User

interface SecureDataStorage {

    fun isUserLeggedIn(): Boolean
    fun clearUser()
    fun saveUser(user: User, key: ByteArray)
    fun getUser(key: ByteArray): User

}
