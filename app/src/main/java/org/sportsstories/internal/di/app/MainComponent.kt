package org.sportsstories.internal.di.app

import androidx.fragment.app.FragmentActivity
import dagger.Component

@Component(modules = [MainModule::class])
interface MainComponent {

    fun inject(mainActivity: FragmentActivity)

}
