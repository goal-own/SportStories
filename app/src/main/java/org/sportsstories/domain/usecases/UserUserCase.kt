package org.sportsstories.domain.usecases

import kotlinx.coroutines.Deferred
import org.sportsstories.domain.model.VkUser
import org.sportsstories.domain.repository.UserRepository
import org.sportsstories.utils.async
import javax.inject.Inject
import javax.inject.Singleton

// TODO may be need to add Inject annotation
@Singleton
class UserUserCase @Inject constructor(
        private val userRepository: UserRepository
) {

    fun authorize(user: VkUser): Deferred<Unit> = async {
        val sessionId = userRepository.getSessionIdAsync(user.userId, user.accessToken)
        userRepository.saveSessionId(sessionId)
    }

    //fun loadUser() : User  = secureStorage.getUser()

}
