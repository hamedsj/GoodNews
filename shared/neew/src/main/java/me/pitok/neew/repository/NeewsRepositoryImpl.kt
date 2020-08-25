package me.pitok.neew.repository

import me.pitok.dependencyinjection.shared.SharedScope
import me.pitok.mapper.Mapper
import me.pitok.neew.api.request.AddNeewRequest
import me.pitok.neew.api.request.ReportRequest
import me.pitok.neew.api.response.NeewApiEntity
import me.pitok.neew.datasource.NeewWritable
import me.pitok.neew.datasource.NeewsReadable
import me.pitok.neew.datasource.ReportWritable
import me.pitok.neew.entity.NeewEntity
import me.pitok.networking.*
import javax.inject.Inject

@SharedScope
class NeewsRepositoryImpl @Inject constructor(
    private val neewWritable: NeewWritable,
    private val neewsReadable: NeewsReadable,
    private val reportWritable: ReportWritable,
    private val mapper: Mapper<NeewApiEntity,NeewEntity>
) : NeewsRepository{

    override suspend fun getNeews(): Response<List<NeewEntity>, Throwable> {
        val getNewsResult = neewsReadable.read()
        return if (getNewsResult.isSuccessful()){
            Success(getNewsResult.getSuccessResponse().value.data.news
                .map(mapper::mapFirstToSecond))
        }else{
            getNewsResult.getFailureResponse()
        }
    }

    override suspend fun addNeew(addNeewRequest: AddNeewRequest): Response<Boolean, Throwable> {
        val addNeewResult = neewWritable.write(addNeewRequest)
        return if (addNeewResult.isSuccessful()){
            Success(true)
        }else{
            addNeewResult.getFailureResponse()
        }
    }

    override suspend fun reportNeew(reportRequest: ReportRequest): Response<Boolean, Throwable> {
        val reportResult = reportWritable.write(reportRequest)
        return if (reportResult.isSuccessful()){
            Success(true)
        }else{
            reportResult.getFailureResponse()
        }
    }

}
