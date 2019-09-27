package org.sportsstories.viewmodel

import android.os.Handler
import androidx.lifecycle.MutableLiveData
import org.sportsstories.domain.model.VkUser
import org.sportsstories.domain.usecases.UserUserCase
import org.sportsstories.internal.routing.navigation.root.RootScreenNavigation
import org.sportsstories.lifecycle.event.ContentEvent
import org.sportsstories.lifecycle_coroutines.CoroutinesViewModel
import ru.terrakok.cicerone.NavigatorHolder
import javax.inject.Inject

class MainViewModel @Inject constructor(
        val navigatorHolder: NavigatorHolder,
        private val rootScreenNavigation: RootScreenNavigation,
        private val userUserCase: UserUserCase
) : CoroutinesViewModel() {

    val loginEvent = MutableLiveData<ContentEvent<Unit>>()

    fun onLogin(user: VkUser) = userUserCase
            .authorize(user)
            .dispatchTo(loginEvent)

    fun openSplashThenStartScreens() {
        rootScreenNavigation.start.openSplash()
        Handler().postDelayed(rootScreenNavigation.start::openLoginFragmentAsRoot, 1000)
    }

}
