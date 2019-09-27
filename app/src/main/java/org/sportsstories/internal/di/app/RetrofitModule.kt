package org.sportsstories.internal.di.app

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.sportsstories.BuildConfig
import org.sportsstories.data.api.BaseApi
import org.sportsstories.data.api.interceptors.ExceptionsInterceptor
import org.sportsstories.data.api.json_adapters.UUIDConverter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

@Module
class RetrofitModule {

    companion object {
        private const val TIMEOUT = 30L
    }

    @Provides
    fun provideMoshi(): Moshi = Moshi
            .Builder()
            .add(UUIDConverter())
            .build()

    @Provides
    fun provideBaseApi(retrofit: Retrofit): BaseApi = retrofit.create(BaseApi::class.java)

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, moshi: Moshi) = Retrofit
            .Builder()
            .baseUrl(BuildConfig.MIDDLE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

    @Provides
    fun provideOkHttpClient(exceptionsInterceptor: ExceptionsInterceptor) = OkHttpClient.Builder()
            .addInterceptor(exceptionsInterceptor)
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
            .also {
                if (BuildConfig.DEBUG) {
                    it.addNetworkInterceptor(HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    })
                }
            }
            .build()

}
