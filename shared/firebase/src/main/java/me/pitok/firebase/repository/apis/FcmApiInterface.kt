package me.pitok.firebase.repository.apis

import me.pitok.firebase.repository.response.FcmRefreshResponse
import me.pitok.networking.Response
import me.pitok.networking.UnAuthenticatedException
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface FcmApiInterface {

    @POST("RefreshFcmToken")
    @FormUrlEncoded
    suspend fun refreshToken(
        @Field("fcm_token") fcm_token: String
    ): Response<FcmRefreshResponse, UnAuthenticatedException>


}