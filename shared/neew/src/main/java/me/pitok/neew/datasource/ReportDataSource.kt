package me.pitok.neew.datasource

import me.pitok.datasource.Writable
import me.pitok.dependencyinjection.shared.SharedScope
import me.pitok.neew.api.NeewsApiInterface
import me.pitok.neew.api.request.ReportRequest
import me.pitok.neew.api.response.NeewsResponse
import me.pitok.networking.*
import me.pitok.networking.response.MessageResponse
import java.io.IOException
import javax.inject.Inject

@SharedScope
class ReportDataSource @Inject constructor(private val neewsApiInterface: NeewsApiInterface)
    : ReportWritable {
    override suspend fun write(input: ReportRequest): Response<MessageResponse, Throwable> {
        try {
            val result = neewsApiInterface.report(input.neew_id)
            return if (result.isSuccessful) {
                Success(requireNotNull(result.body()))
            } else {
                if(result.code() == 401){
                    Failure(CommonExceptions.UnAuthenticatedException)
                }else{
                    Failure(Throwable())
                }
            }
        }catch (t: Throwable){
            return when(t){
                is IOException -> Failure(CommonExceptions.ConnectionException)
                else -> Failure(t)
            }
        }
    }
}

typealias ReportWritable = Writable.Suspendable.IO<ReportRequest,
        @JvmSuppressWildcards Response<MessageResponse,Throwable>>