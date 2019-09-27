package org.sportsstories.internal.di.app

import dagger.Module
import dagger.Provides
import org.sportsstories.internal.routing.AppRouter
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router

@Module
class RootNavigationModule {

    @Provides
    fun provideCicerone(router: AppRouter): Cicerone<Router> = Cicerone.create(router)

    @Provides
    fun provideNavigatorHolder(cicerone: Cicerone<Router>): NavigatorHolder = cicerone.navigatorHolder

    @Provides
    fun provideRouter(cicerone: Cicerone<Router>): Router = cicerone.router

}
