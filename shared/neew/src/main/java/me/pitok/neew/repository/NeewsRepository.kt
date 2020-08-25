package me.pitok.neew.repository

import me.pitok.neew.api.request.AddNeewRequest
import me.pitok.neew.api.request.ReportRequest
import me.pitok.neew.entity.NeewEntity
import me.pitok.networking.Response

interface NeewsRepository {

    suspend fun getNeews(): Response<List<NeewEntity>, Throwable>

    suspend fun addNeew(addNeewRequest: AddNeewRequest): Response<Boolean, Throwable>

    suspend fun reportNeew(reportRequest: ReportRequest): Response<Boolean, Throwable>
}