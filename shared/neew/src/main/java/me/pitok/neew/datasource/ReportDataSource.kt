package me.pitok.neew.datasource

import me.pitok.datasource.Writable
import me.pitok.dependencyinjection.shared.SharedScope
import me.pitok.neew.api.NeewsApiInterface
import me.pitok.neew.api.request.ReportRequest
import me.pitok.networking.Response
import me.pitok.networking.UnAuthenticatedException
import me.pitok.networking.response.MessageResponse
import javax.inject.Inject

@SharedScope
class ReportDataSource @Inject constructor(private val neewsApiInterface: NeewsApiInterface)
    : ReportWritable {
    override suspend fun write(input: ReportRequest): Response<MessageResponse, UnAuthenticatedException> {
        return neewsApiInterface.report(input.neew_id)
    }
}

typealias ReportWritable = Writable.Suspendable.IO<ReportRequest,
        @JvmSuppressWildcards Response<MessageResponse,UnAuthenticatedException>>