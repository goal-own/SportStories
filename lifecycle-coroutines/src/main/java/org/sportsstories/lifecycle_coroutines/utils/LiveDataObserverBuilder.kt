package org.sportsstories.lifecycle_coroutines.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import org.sportsstories.lifecycle.event.ContentEvent

class LiveDataObserverBuilder<T>(
        private val liveData: LiveData<ContentEvent<T>>
) {

    private var onLoading: () -> Unit = ::emptyAction
    private var onSuccess: (T) -> Unit = ::emptyConsumer
    private var onError: (Throwable) -> Unit = ::emptyErrorConsumer
    private var onComplete: (T?) -> Unit = ::emptyConsumer

    companion object {

        fun <T> builder(liveData: LiveData<ContentEvent<T>>) = LiveDataObserverBuilder(liveData)

    }

    fun onLoading(onLoading: () -> Unit): LiveDataObserverBuilder<T> {
        this.onLoading = onLoading
        return this
    }

    fun onSuccess(onSuccess: (T) -> Unit): LiveDataObserverBuilder<T> {
        this.onSuccess = onSuccess
        return this
    }

    fun onError(onError: (Throwable) -> Unit): LiveDataObserverBuilder<T> {
        this.onError = onError
        return this
    }

    fun onComplete(onComplete: (T?) -> Unit): LiveDataObserverBuilder<T> {
        this.onComplete = onComplete
        return this
    }

    fun subscribe(lifecycleOwner: LifecycleOwner) {
        liveData.observe(lifecycleOwner, Observer {
            when (it) {
                is ContentEvent.Loading -> onLoading.invoke()
                is ContentEvent.Success -> onSuccess.invoke(it.data)
                is ContentEvent.Error -> onError.invoke(it.throwable)
                is ContentEvent.Complete -> onComplete.invoke(it.data)
            }
        })
    }

}
