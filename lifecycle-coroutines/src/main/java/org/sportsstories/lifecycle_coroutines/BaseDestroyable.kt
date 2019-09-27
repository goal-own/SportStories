package org.sportsstories.lifecycle_coroutines

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class BaseDestroyable : Destroyable {

    private val compositeJob = CompositeJob()

    override fun onDestroy() {
        compositeJob.cancel()
    }

    override fun <T> Deferred<T>.untilDestroy(
        onNext: (T) -> Unit,
        onComplete: () -> Unit,
        onError: (Throwable) -> Unit
    ): Job = GlobalScope
        .launch(Main) {
            try {
                val res = await()
                onNext.invoke(res)
            } catch (throwable: Throwable) {
                onError.invoke(throwable)
            } finally {
                onComplete.invoke()
            }
        }
        .also { compositeJob.add(it) }

}
