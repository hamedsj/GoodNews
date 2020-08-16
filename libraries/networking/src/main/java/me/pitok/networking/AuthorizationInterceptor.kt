package me.pitok.networking

import me.pitok.datasource.Readable
import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor(private val tokenReader: Readable<TokenEntity>): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .header("Authorization", tokenReader.read().accessToken)
            .method(chain.request().method(),chain.request().body())
            .build()
        return chain.proceed(request)
    }

}