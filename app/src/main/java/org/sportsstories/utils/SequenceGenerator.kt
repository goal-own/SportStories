package org.sportsstories.utils

import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicLong

object SequenceGenerator {

    private val intSequence = AtomicInteger(0)
    private val longSequence = AtomicLong(0)

    fun nextLong(): Long {
        return longSequence.incrementAndGet()
    }

    fun nextInt(): Int {
        return intSequence.incrementAndGet()
    }

}
