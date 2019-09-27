package org.sportsstories.lifecycle_coroutines.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import org.sportsstories.lifecycle.event.ContentEvent
import org.sportsstories.lifecycle_coroutines.utils.LiveDataObserverBuilder
import org.sportsstories.lifecycle_coroutines.utils.emptyAction
import org.sportsstories.lifecycle_coroutines.utils.emptyConsumer
import org.sportsstories.lifecycle_coroutines.utils.emptyErrorConsumer

fun <T> LifecycleOwner.observeLiveData(liveData: LiveData<ContentEvent<T>>) = LiveDataObserverBuilder.builder(liveData)

fun <T> LifecycleOwner.observeLiveData(liveData: LiveData<T>, observer: (T) -> Unit) {
    liveData.observe(this, Observer {
        observer.invoke(it)
    })
}

fun <T> LifecycleOwner.observeLiveData(
        liveData: LiveData<ContentEvent<T>>,
        onLoading: () -> Unit = ::emptyAction,
        onSuccess: (T) -> Unit = ::emptyConsumer,
        onError: (Throwable) -> Unit = ::emptyErrorConsumer,
        onComplete: () -> Unit = ::emptyAction
) {
    liveData.observe(this, Observer {
        when (it) {
            is ContentEvent.Loading -> onLoading.invoke()
            is ContentEvent.Success -> onSuccess.invoke(it.data)
            is ContentEvent.Error -> onError.invoke(it.throwable)
            is ContentEvent.Complete -> onComplete.invoke()
        }
    })
}
