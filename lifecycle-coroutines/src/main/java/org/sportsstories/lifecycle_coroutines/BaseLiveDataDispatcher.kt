package org.sportsstories.lifecycle_coroutines

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Job
import org.sportsstories.lifecycle.event.ContentEvent

class BaseLiveDataDispatcher(
    private val destroyable: Destroyable
) : LiveDateDispatcher, Destroyable by destroyable {

    override fun <T> Deferred<T>.dispatchTo(liveData: MutableLiveData<ContentEvent<T>>): Job {
        liveData.value = ContentEvent.Loading(liveData.value?.data)
        return this.untilDestroy(
            onNext = { data -> liveData.value = (ContentEvent.Success(data)) },
            onError = { throwable -> liveData.value = (ContentEvent.Error(throwable)) },
            onComplete = { liveData.value = (ContentEvent.Complete(liveData.value?.data)) }
        )
    }

}
