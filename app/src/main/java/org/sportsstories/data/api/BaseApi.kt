package org.sportsstories.data.api

import okhttp3.RequestBody
import org.sportsstories.data.api.model.BaseResponseNW
import org.sportsstories.data.api.model.auth.SessionIdNW
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface BaseApi {

    @GET(ApiPath.Login.login)
    suspend fun login(
            @Query(ApiPath.PlaceHolder.accessToken) accessToken: String,
            @Query(ApiPath.PlaceHolder.userId) userId: Int
    ): BaseResponseNW<SessionIdNW>

    @POST(ApiPath.Stories.upload)
    suspend fun uploadStories(
            @Query(ApiPath.PlaceHolder.sessionId) sessionId: String,
            @Body body: RequestBody
    )

    @GET(ApiPath.Login.checkSession)
    suspend fun checkSessionId(
            @Query(ApiPath.PlaceHolder.sessionId) sessionId: String
    ): BaseResponseNW<Boolean>

}
