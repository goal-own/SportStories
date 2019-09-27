package org.sportsstories.lifecycle_coroutines

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Job
import org.sportsstories.lifecycle.event.ContentEvent

interface LiveDateDispatcher {

    fun <T> Deferred<T>.dispatchTo(liveData: MutableLiveData<ContentEvent<T>>): Job

}
