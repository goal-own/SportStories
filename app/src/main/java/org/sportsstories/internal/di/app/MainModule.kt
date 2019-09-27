package org.sportsstories.internal.di.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import org.sportsstories.internal.di.app.viewmodel.ViewModelFactory
import org.sportsstories.internal.di.app.viewmodel.ViewModelKey
import org.sportsstories.viewmodel.LoginViewModel
import org.sportsstories.viewmodel.MainScreenViewModel
import org.sportsstories.viewmodel.MainViewModel

@Module
interface MainModule {

    @Binds
    fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    fun bindLoginViewModel(viewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainScreenViewModel::class)
    fun bindMainScreenViewModel(viewModel: MainScreenViewModel): ViewModel

}
