package org.sportsstories.internal.routing.navigation.root

import org.sportsstories.internal.routing.AppRouter
import org.sportsstories.internal.routing.navigation.ScreenNavigation
import org.sportsstories.internal.routing.navigation.root.main.StartScreenNavigation
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RootScreenNavigation @Inject constructor(
        router: AppRouter
) : ScreenNavigation(router) {

    val start = nav { StartScreenNavigation(it) }

}
