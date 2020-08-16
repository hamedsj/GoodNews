package me.pitok.networking.response

import com.google.gson.annotations.SerializedName
import me.pitok.networking.TokenEntity

data class TokenResponseData(@SerializedName("access_token") val accessToken: String,
                           @SerializedName("refresh_token") val refreshToken: String)

data class TokenResponse(@SerializedName("message") val message: String,
                       @SerializedName("data") val data: TokenResponseData)
