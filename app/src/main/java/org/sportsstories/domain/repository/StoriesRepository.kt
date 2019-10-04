package org.sportsstories.domain.repository

import kotlinx.coroutines.Deferred
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.asRequestBody
import org.sportsstories.data.api.BaseApi
import org.sportsstories.domain.model.Stories
import org.sportsstories.domain.model.camera.LocalAttachment
import org.sportsstories.presentation.base.stories.StoriesItem
import org.sportsstories.utils.async
import java.io.File
import javax.inject.Inject

class StoriesRepository @Inject constructor(
        private val baseApi: BaseApi
) {

    suspend fun uploadStories(sessionId: String, localAttachment: LocalAttachment) = baseApi
            .uploadStories(
                    sessionId,
                    File(localAttachment.fullPath).asRequestBody(localAttachment.mimeType.toMediaTypeOrNull())
            )

    //TODO (Remove mock method)
    fun getStoriesAsync(): Deferred<List<StoriesItem>> = async {
        listOf(StoriesItem.AddStories) + list.map { StoriesItem.FriendsStories(it) }
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
