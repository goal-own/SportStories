package org.sportsstories.data.api

import org.sportsstories.data.api.model.BaseResponseNW
import org.sportsstories.data.api.model.auth.SessionIdNW
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface BaseApi {

    @GET(ApiPath.Login.login)
    suspend fun login(
            @Query(ApiPath.PlaceHolder.accessToken) accessToken: String,
            @Query(ApiPath.PlaceHolder.userId) userId: Int
    ): BaseResponseNW<SessionIdNW>

    @POST(ApiPath.Stories.upload)
    @Headers(value = ["Content-Type:image/jpeg"])
    suspend fun uploadStories(
            @Query(ApiPath.PlaceHolder.sessionId) sessionId: String,
            @Body array: ByteArray
    )

    @GET(ApiPath.Login.checkSession)
    suspend fun checkSessionId(
            @Query(ApiPath.PlaceHolder.sessionId) sessionId: String
    ): BaseResponseNW<Boolean>

}
