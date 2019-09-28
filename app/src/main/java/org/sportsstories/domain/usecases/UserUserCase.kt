package org.sportsstories.domain.usecases

import kotlinx.coroutines.Deferred
import org.sportsstories.domain.model.User
import org.sportsstories.domain.model.VkUser
import org.sportsstories.domain.repository.UserRepository
import org.sportsstories.utils.async
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserUserCase @Inject constructor(
        private val userRepository: UserRepository
) {

    var user = userRepository.loadUser()
        private set

    fun checkIfSessionIdIsActiveAsync(): Deferred<Boolean> = async {
        user?.sessionId?.let { userRepository.checkSessionIdAsync(it) } ?: false
    }

    fun authorizeAsync(user: VkUser): Deferred<Unit> = async {
        val sessionId = userRepository.getSessionIdAsync(user.userId, user.accessToken)
        userRepository.saveUser(
                User(sessionId, user.userId, user.accessToken)
        )
    }

}
