package org.sportsstories.domain.model.camera

data class LocalAttachment(
        val id: String,
        val mimeType: String,
        val fileName: String,
        val fullPath: String,
        val size: Long
)
