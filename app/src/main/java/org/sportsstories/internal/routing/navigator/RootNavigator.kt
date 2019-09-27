package org.sportsstories.internal.routing.navigator

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import org.sportsstories.R
import org.sportsstories.presentation.fragments.splash.SplashScreenFragment
import ru.terrakok.cicerone.commands.Command

class RootNavigator(
        activity: FragmentActivity,
        @IdRes containerId: Int
) : ScreenNavigator(activity, containerId) {

    override fun setupFragmentTransaction(
            command: Command,
            currentFragment: Fragment?,
            nextFragment: Fragment?,
            fragmentTransaction: FragmentTransaction
    ) {
        when (currentFragment) {
            is SplashScreenFragment -> fragmentTransaction.fade()
        }
    }

    private fun FragmentTransaction.slide(withEnter: Boolean = true) {
        setCustomAnimations(
                if (withEnter) R.anim.enter_from_right else 0,
                R.anim.exit_to_left,
                R.anim.enter_from_left,
                R.anim.exit_to_right)
    }

    @Suppress("detekt.UnusedPrivateMember")
    private fun FragmentTransaction.slideFromBottom(withEnter: Boolean = true) {
        setCustomAnimations(
                if (withEnter) R.anim.slide_up else 0,
                R.anim.fade_out,
                R.anim.fade_in,
                R.anim.slide_down)
    }

    private fun FragmentTransaction.fade() {
        setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
    }

}
