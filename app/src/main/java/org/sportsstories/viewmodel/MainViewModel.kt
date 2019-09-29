package org.sportsstories.viewmodel

import org.sportsstories.internal.routing.navigation.root.RootScreenNavigation
import org.sportsstories.lifecycle_coroutines.CoroutinesViewModel
import ru.terrakok.cicerone.NavigatorHolder
import javax.inject.Inject

class MainViewModel @Inject constructor(
        val navigatorHolder: NavigatorHolder,
        private val rootScreenNavigation: RootScreenNavigation
) : CoroutinesViewModel() {

    fun openSplash() {
        rootScreenNavigation.start.openSplash()
    }


}
