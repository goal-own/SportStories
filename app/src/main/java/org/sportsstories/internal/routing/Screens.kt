package org.sportsstories.internal.routing

import org.sportsstories.presentation.fragments.camera.ShootStoriesFragment
import org.sportsstories.presentation.fragments.login.LoginFragment
import org.sportsstories.presentation.fragments.main.MainFragment
import org.sportsstories.presentation.fragments.splash.SplashScreenFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class LoginScreen : SupportAppScreen() {
    override fun getFragment() = LoginFragment.newInstance()
}

class SplashScreen : SupportAppScreen() {
    override fun getFragment() = SplashScreenFragment.newInstance()
}

class MainScreen : SupportAppScreen() {
    override fun getFragment() = MainFragment.newInstance()
}

class ShootStoriesScreen : SupportAppScreen() {
    override fun getFragment() = ShootStoriesFragment.newInstance()
}