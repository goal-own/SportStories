package org.sportsstories.lifecycle_coroutines.utils

fun emptyAction(): Unit = Unit

@Suppress("detekt.UnusedPrivateMember")
fun <T> emptyConsumer(t: T) = Unit

@Suppress("detekt.UnusedPrivateMember")
fun emptyErrorConsumer(t: Throwable) = Unit
