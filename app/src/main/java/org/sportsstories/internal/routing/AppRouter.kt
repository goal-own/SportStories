package org.sportsstories.internal.routing

import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.commands.Back
import ru.terrakok.cicerone.commands.Command
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRouter @Inject constructor() : Router() {

    fun back(deep: Int = 1) {
        executeCommands(deep * { Back() })
    }

    private fun executeCommands(commands: List<Command>) {
        executeCommands(*commands.toTypedArray())
    }

    @Suppress("detekt.UnusedPrivateMember")
    private operator fun <T> Int.times(provider: () -> T): List<T> {
        if (this < 1) return emptyList()
        if (this == 1) return listOf(provider.invoke())

        return (0 until this).fold(mutableListOf()) { acc, _ ->
            acc.add(provider.invoke())
            acc
        }
    }

}
