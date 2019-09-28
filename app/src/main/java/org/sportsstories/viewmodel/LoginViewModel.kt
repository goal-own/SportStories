package org.sportsstories.viewmodel

import androidx.lifecycle.MutableLiveData
import org.sportsstories.domain.model.VkUser
import org.sportsstories.domain.usecases.UserUserCase
import org.sportsstories.internal.routing.navigation.root.RootScreenNavigation
import org.sportsstories.lifecycle.event.ContentEvent
import org.sportsstories.lifecycle_coroutines.CoroutinesViewModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginViewModel @Inject constructor(
        private val rootScreenNavigation: RootScreenNavigation,
        private val userUserCase: UserUserCase
) : CoroutinesViewModel() {

    val loginEvent = MutableLiveData<ContentEvent<Unit>>()

    fun openMainScreen() {
        rootScreenNavigation.start.openMainScreenAsRoot()
    }

    fun onLogin(user: VkUser) = userUserCase
            .authorizeAsync(user)
            .dispatchTo(loginEvent)

}
