package me.pitok.neew.api.response

import com.google.gson.annotations.SerializedName


data class NeewApiEntity(
    @SerializedName("new_id") val _id: Int,
    @SerializedName("content") val content: String,
    @SerializedName("data") val timestamp: String
)

data class NeewsListApiEntity(
    @SerializedName("news") val news: List<NeewApiEntity>
)

data class NeewsResponse(
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: NeewsListApiEntity
)