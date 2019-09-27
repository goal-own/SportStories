package org.sportsstories

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import com.vk.api.sdk.VK
import net.danlew.android.joda.JodaTimeAndroid
import org.sportsstories.internal.di.app.AppModule
import org.sportsstories.internal.di.app.DaggerAppComponent
import org.sportsstories.internal.di.app.viewmodel.ProviderHolder
import org.sportsstories.presentation.Animations
import javax.inject.Inject

class App : Application(), ProviderHolder {

    @Inject
    override lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate() {
        super.onCreate()
        initializeDagger()
        initializeVk()
        Animations.init(this)
        JodaTimeAndroid.init(this)
    }

    private fun initializeDagger() {
        DaggerAppComponent.builder().appModule(AppModule(this)).build().inject(this)
    }

    private fun initializeVk() {
        VK.initialize(this)
    }

}
