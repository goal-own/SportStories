package org.sportsstories.internal.routing.navigation

import org.sportsstories.internal.routing.AppRouter

open class ScreenNavigation(val router: AppRouter) {

    private val navs = mutableListOf<ScreenNavigation>()

    fun back(deep: Int = 1) {
        router.back(deep)
    }

    protected fun <N : ScreenNavigation> nav(
            navigationProvider: (AppRouter) -> N
    ): N {
        val currentNavigation = this
        val navigation = navigationProvider.invoke(router)
        currentNavigation.navs.add(navigation)
        return navigation
    }

}
