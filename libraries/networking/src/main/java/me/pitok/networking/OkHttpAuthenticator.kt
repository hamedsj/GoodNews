package me.pitok.networking

import me.pitok.datasource.Readable
import me.pitok.datasource.Writable
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class OkHttpAuthenticator constructor(private val tokenRefresher: Readable<TokenEntity>,
                                      private val tokenWriter: Writable<TokenEntity>,
                                      private val tokenReader: Readable<TokenEntity>): Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        return try {
            with(tokenRefresher.read()) {
                tokenWriter.write(this)
                response.request().newBuilder()
                    .header("AUTHORIZATION", tokenReader.read().accessToken).build()
            }
        }catch (e: Throwable){
            null
        }
    }

}