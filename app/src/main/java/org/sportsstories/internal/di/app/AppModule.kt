package org.sportsstories.internal.di.app

import android.content.Context
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import org.sportsstories.App
import org.sportsstories.data.storage.DataStorage
import org.sportsstories.data.storage.impl.DataStorageImpl

@Module
class AppModule(private val context: App) {

    @Provides
    fun provideContext(): Context = context

    @Provides
    fun provideDataStorage(moshi: Moshi, context: Context): DataStorage = DataStorageImpl(moshi, context)

}
