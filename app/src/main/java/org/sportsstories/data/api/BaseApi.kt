package org.sportsstories.data.api

import org.sportsstories.data.api.model.BaseResponseNW
import org.sportsstories.data.api.model.auth.SessionIdNW
import retrofit2.http.GET
import retrofit2.http.Query

interface BaseApi {

    @GET(ApiPath.Login.login)
    suspend fun login(
            @Query(ApiPath.PlaceHolder.accessToken) accessToken: String,
            @Query(ApiPath.PlaceHolder.userId) userId: Int
    ): BaseResponseNW<SessionIdNW>

}
