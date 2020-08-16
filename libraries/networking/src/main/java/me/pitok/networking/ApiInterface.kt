package me.pitok.networking

import me.pitok.networking.response.TokenResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiInterface {

    @POST("RefreshToken")
    @FormUrlEncoded
    fun refreshToken( @Field("refresh_token") refresh_token: String): Response<TokenResponse, UnAuthenticatedException>
}
