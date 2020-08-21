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
import javax.inject.Inject

@SharedScope
class NeewsDataSource @Inject constructor(
    private val neewsApiInterface: NeewsApiInterface
) : NeewWritable, NeewsReadable{

    override suspend fun write(input: AddNeewRequest): Response<MessageResponse,
            UnAuthenticatedException> {
        return when(input.mode){
            is NeewAddType.ByContent -> {
                neewsApiInterface.addNeewByContent(input.content)
            }
            is NeewAddType.ByLink -> {
                neewsApiInterface.addNeewByTWLink(input.content)
            }
        }
    }

    override suspend fun read(): Response<NeewsResponse, UnAuthenticatedException> {
        return neewsApiInterface.getNeews()
    }

}

typealias NeewWritable = Writable.Suspendable.IO<AddNeewRequest,
        @JvmSuppressWildcards Response<MessageResponse, UnAuthenticatedException>>

typealias NeewsReadable = Readable.Suspendable<Response<NeewsResponse, UnAuthenticatedException>>