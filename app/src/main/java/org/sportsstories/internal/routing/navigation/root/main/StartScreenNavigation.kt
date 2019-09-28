package org.sportsstories.internal.routing.navigation.root.main

import org.sportsstories.internal.routing.AppRouter
import org.sportsstories.internal.routing.LoginScreen
import org.sportsstories.internal.routing.MainScreen
import org.sportsstories.internal.routing.SplashScreen
import org.sportsstories.internal.routing.navigation.ScreenNavigation

class StartScreenNavigation(router: AppRouter) : ScreenNavigation(router) {

    fun openLoginFragmentAsRoot() {
        router.newRootScreen(LoginScreen())
    }

    fun openSplash() {
        router.newRootScreen(SplashScreen())
    }

    fun openMainScreenAsRoot() {
        router.newRootScreen(MainScreen())
    }

}
