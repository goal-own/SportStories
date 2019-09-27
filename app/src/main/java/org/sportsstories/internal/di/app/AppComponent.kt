package org.sportsstories.internal.di.app

import dagger.Component
import org.sportsstories.App
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, RootNavigationModule::class, MainModule::class, RetrofitModule::class])
interface AppComponent {
    fun inject(context: App)
}
