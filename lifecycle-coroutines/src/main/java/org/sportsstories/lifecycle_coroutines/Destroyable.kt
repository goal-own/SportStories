package org.sportsstories.lifecycle_coroutines

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Job

interface Destroyable {

    fun onDestroy()

    fun <T> Deferred<T>.untilDestroy(
        onNext: (T) -> Unit,
        onComplete: () -> Unit,
        onError: (Throwable) -> Unit
    ) : Job

}
