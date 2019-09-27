package org.sportsstories.data.di

import dagger.Component
import org.sportsstories.data.ApiTests
import org.sportsstories.internal.di.app.AppModule
import org.sportsstories.internal.di.app.MainModule
import org.sportsstories.internal.di.app.RetrofitModule
import org.sportsstories.internal.di.app.RootNavigationModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, RootNavigationModule::class, MainModule::class, RetrofitModule::class])
interface AppTestComponent {
    fun inject(apiTests: ApiTests)
}
