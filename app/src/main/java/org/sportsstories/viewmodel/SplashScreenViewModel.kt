package org.sportsstories.viewmodel

import androidx.lifecycle.MutableLiveData
import org.sportsstories.domain.usecases.UserUserCase
import org.sportsstories.internal.routing.navigation.root.RootScreenNavigation
import org.sportsstories.lifecycle.event.ContentEvent
import org.sportsstories.lifecycle_coroutines.CoroutinesViewModel
import javax.inject.Inject

class SplashScreenViewModel @Inject constructor(
        private val rootScreenNavigation: RootScreenNavigation,
        private val userUserCase: UserUserCase
) : CoroutinesViewModel() {

    val isSessionIdActiveEvent = MutableLiveData<ContentEvent<Boolean>>()

    init {
        checkIfUserLoggedIn()
    }

    fun openLoginOrMainScreen(isSessionActive: Boolean) {
        if (isSessionActive) {
            rootScreenNavigation.start.openMainScreenAsRoot()
        } else {
            rootScreenNavigation.start.openLoginFragmentAsRoot()
        }
    }

    private fun checkIfUserLoggedIn() {
        userUserCase.checkIfSessionIdIsActiveAsync().dispatchTo(isSessionIdActiveEvent)
    }

}
