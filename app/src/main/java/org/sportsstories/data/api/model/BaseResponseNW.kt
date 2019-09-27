package org.sportsstories.data.api.model

class BaseResponseNW<T>(
        val data: T,
        val errorCode: Int
)
