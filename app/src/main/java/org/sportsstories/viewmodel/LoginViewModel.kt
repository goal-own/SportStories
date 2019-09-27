package org.sportsstories.viewmodel

import org.sportsstories.internal.routing.navigation.root.RootScreenNavigation
import org.sportsstories.lifecycle_coroutines.CoroutinesViewModel
import javax.inject.Inject

class LoginViewModel @Inject constructor(
        private val rootScreenNavigation: RootScreenNavigation
) : CoroutinesViewModel() {

    fun openMainScreen() {
        rootScreenNavigation.start.openMainScreen()
    }

}
