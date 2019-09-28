package org.sportsstories.internal.di.app

import android.content.Context
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import org.sportsstories.App
import org.sportsstories.data.storage.DataStorage
import org.sportsstories.data.storage.impl.DataStorageImpl
import org.sportsstories.domain.data_provider.IdentityGenerator
import org.sportsstories.domain.data_provider.PhotoFileResourceManager
import org.sportsstories.domain.data_provider.ResourceManager
import org.sportsstories.domain.data_provider.UriBitmapProcessor
import org.sportsstories.domain.data_provider.UriFileProcessor
import org.sportsstories.domain.data_provider.impl.IdentityGeneratorImpl
import org.sportsstories.domain.data_provider.impl.PhotoFileResourceManagerImpl
import org.sportsstories.domain.data_provider.impl.ResourceManagerImpl
import org.sportsstories.domain.data_provider.impl.UriBitmapProcessorImpl
import org.sportsstories.domain.data_provider.impl.UriFileProcessorImpl

@Module
class AppModule(private val context: App) {

    @Provides
    fun provideContext(): Context = context

    @Provides
    fun provideDataStorage(moshi: Moshi, context: Context): DataStorage =
            DataStorageImpl(moshi, context)

    @Provides
    fun provideUriFileProcessor(context: Context): UriFileProcessor =
            UriFileProcessorImpl(context)

    @Provides
    fun provideUriBitmapProcessor(context: Context): UriBitmapProcessor =
            UriBitmapProcessorImpl(context)

    @Provides
    fun provideIdentityGenerator(): IdentityGenerator = IdentityGeneratorImpl()

    @Provides
    fun provideResourceManager(context: Context): ResourceManager = ResourceManagerImpl(context)

    @Provides
    fun providePhotoFileResourceManager(resourceManager: ResourceManager): PhotoFileResourceManager = PhotoFileResourceManagerImpl(resourceManager)

}
