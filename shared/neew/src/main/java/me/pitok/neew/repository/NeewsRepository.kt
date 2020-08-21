package me.pitok.neew.repository

import me.pitok.neew.api.request.AddNeewRequest
import me.pitok.neew.api.request.ReportRequest
import me.pitok.neew.entity.NeewEntity
import me.pitok.networking.Response
import me.pitok.networking.UnAuthenticatedException
import me.pitok.networking.response.MessageResponse

interface NeewsRepository {

    suspend fun getNeews(): Response<List<NeewEntity>, UnAuthenticatedException>

    suspend fun addNeew(addNeewRequest: AddNeewRequest): Response<Boolean, UnAuthenticatedException>

    suspend fun reportNeew(reportRequest: ReportRequest): Response<Boolean, UnAuthenticatedException>
}