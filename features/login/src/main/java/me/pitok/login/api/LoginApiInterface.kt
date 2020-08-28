package me.pitok.login.api

import me.pitok.login.api.responses.LoginResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface LoginApiInterface {

    @POST("login")
    @FormUrlEncoded
    suspend fun login(@Field("username") username: String,
                      @Field("password") password: String): Response<LoginResponse>

    @POST("signup")
    @FormUrlEncoded
    suspend fun signup(@Field("username") username: String,
                      @Field("password") password: String): Response<LoginResponse>

}