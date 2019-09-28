package org.sportsstories.extensions

import java.io.File

fun File.toByteArray() = inputStream().readBytes()
