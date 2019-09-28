package org.sportsstories.data.storage

import org.sportsstories.domain.model.User

interface DataStorage {

    fun storeUser(user: User)
    fun loadUser(): User?

}
