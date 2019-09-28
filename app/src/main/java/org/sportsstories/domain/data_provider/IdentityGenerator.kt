package org.sportsstories.domain.data_provider

import java.util.UUID

interface IdentityGenerator {

    fun generateUUID(): UUID

    fun generateUniqueString(): String

}
