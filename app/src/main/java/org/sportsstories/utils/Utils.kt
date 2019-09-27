package org.sportsstories.utils

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.logging.Logger

fun <B> async(block: suspend () -> B): Deferred<B> = GlobalScope.async(Dispatchers.IO) {
    block.invoke()
}

suspend fun <B> withIO(block: suspend () -> B) = withContext(Dispatchers.IO) {
    block.invoke()
}

@Suppress("detekt.TooGenericExceptionCaught")
fun <B> launch(block: suspend () -> B): Job = GlobalScope.launch(Dispatchers.IO) {
    try {
        block.invoke()
    } catch (throwable: Throwable) {
        Logger.getLogger("Utils").warning("Caught ${throwable.javaClass.canonicalName}")
    }
}
