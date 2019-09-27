package org.sportsstories.data.api.exceptions

import org.sportsstories.data.api.ApiError
import java.io.IOException

class ServerException(private val code: Int, message: String? = null) : IOException(message) {

    companion object {
        private val codeToErrorTypeMap = mapOf(
                1 to ApiError.INVALID_PARAMETERS
        )

        fun getErrorTypeByCode(code: Int) = codeToErrorTypeMap[code]
    }

    fun getErrorType(): ApiError? = codeToErrorTypeMap[code]

}
