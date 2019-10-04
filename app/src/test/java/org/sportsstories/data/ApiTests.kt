package org.sportsstories.data

import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.Mockito.mock
import org.sportsstories.App
import org.sportsstories.data.api.BaseApi
import org.sportsstories.data.di.DaggerAppTestComponent
import org.sportsstories.internal.di.app.AppModule
import javax.inject.Inject

class ApiTests {

    @Inject
    lateinit var api: BaseApi

    init {
        DaggerAppTestComponent.builder().appModule(AppModule(mock(App::class.java))).build()
                .inject(this)
    }

    @Test
    fun testQuotes() {
        runBlocking {
            val sessionId = api.login("qweqwewqe", 12).data.sessionId
            println(sessionId)
        }
    }

}
