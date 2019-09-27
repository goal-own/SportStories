package org.sportsstories.domain.repository

import kotlinx.coroutines.Deferred
import org.sportsstories.domain.model.Stories
import org.sportsstories.utils.async
import javax.inject.Inject

class StoriesRepository @Inject constructor() {

    //TODO (Remove mock method)
    fun getStoriesAsync(): Deferred<List<Stories>> = async {
        list
    }

    private val fnames = listOf("Ann", "Max", "Alex", "Pasha", "Lesha", "Rus")
    private val snames = listOf("Vlasov", "Fox", "Lex", "Fog", "Lee", "Bee")

    private val list = List(25) { i ->
        Stories(
            12321,
            fnames.random(),
            snames.random(),
            "https://raw.githubusercontent.com/Hiraev/pic-loaders-benchmark/master/images/small/${i + 1}.jpg"
        )
    }

}
