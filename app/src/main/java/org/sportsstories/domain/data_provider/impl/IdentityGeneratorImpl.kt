package org.sportsstories.domain.data_provider.impl

import org.sportsstories.domain.data_provider.IdentityGenerator
import java.util.UUID

class IdentityGeneratorImpl : IdentityGenerator {

    override fun generateUUID() = UUID.randomUUID()

    override fun generateUniqueString() = generateUUID().toString().toLowerCase()

}
