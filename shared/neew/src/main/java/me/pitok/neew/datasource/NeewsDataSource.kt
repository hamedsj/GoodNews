package me.pitok.neew.datasource

import me.pitok.datasource.Readable
import me.pitok.datasource.Writable
import me.pitok.dependencyinjection.shared.SharedScope
import me.pitok.neew.NeewAddType
import me.pitok.neew.api.NeewsApiInterface
import me.pitok.neew.api.response.NeewsResponse
import me.pitok.neew.api.request.AddNeewRequest
import me.pitok.networking.*
import me.pitok.networking.response.MessageResponse
import java.io.IOException
import javax.inject.Inject

@SharedScope
class NeewsDataSource @Inject constructor(
    private val neewsApiInterface: NeewsApiInterface
) : NeewWritable, NeewsReadable{

    override suspend fun write(input: AddNeewRequest): Response<MessageResponse, Throwable> {
        try {
            val result = when(input.mode){
                is NeewAddType.ByContent -> {
                    neewsApiInterface.addNeewByContent(input.content)
                }
                is NeewAddType.ByLink -> {
                    neewsApiInterface.addNeewByTWLink(input.content)
                }
            }
            return if (result.isSuccessful){
                Success(requireNotNull(result.body()))
            }else{
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

    override suspend fun read(): Response<NeewsResponse, Throwable> {
        try {
            val neews = neewsApiInterface.getNeews()
            return if (neews.isSuccessful) {
                Success<NeewsResponse>(requireNotNull(neews.body()))
            } else {
                if(neews.code() == 401){
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

typealias NeewWritable = Writable.Suspendable.IO<AddNeewRequest,
        @JvmSuppressWildcards Response<MessageResponse, Throwable>>

typealias NeewsReadable = Readable.Suspendable<@JvmSuppressWildcards Response<NeewsResponse, Throwable>>