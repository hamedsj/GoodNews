package me.pitok.login.api.responses

import com.google.gson.annotations.SerializedName

data class LoginApiEntity(
    @SerializedName("access_token") val access_token: String,
    @SerializedName("refresh_token") val refresh_token: String
)

data class LoginResponse(
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: LoginApiEntity
)