package me.pitok.login.datasource

import me.pitok.datasource.Writable
import me.pitok.login.api.LoginApiInterface
import me.pitok.login.api.requests.LoginRequest
import me.pitok.networking.CommonExceptions
import me.pitok.networking.Failure
import me.pitok.networking.Response
import me.pitok.networking.Success
import me.pitok.sharedpreferences.Keys
import me.pitok.sharedpreferences.StoreModel
import me.pitok.sharedpreferences.di.qulifiers.TokenSP
import me.pitok.sharedpreferences.typealiases.SpWriter
import java.io.IOException
import javax.inject.Inject

class LoginDataSource @Inject constructor(private val loginApiInterface: LoginApiInterface,
                                          @TokenSP private val tokenWriter: SpWriter) : LoginWritable {

    override suspend fun write(input: LoginRequest): Response<Boolean,Throwable> {
        return try {
            val result = loginApiInterface.login(input.username, input.password)
            if (result.isSuccessful){
                result.body()?.data
                    ?.run {
                        tokenWriter.write(StoreModel(Keys.ACCESS_TOKEN_KEY, access_token))
                        tokenWriter.write(StoreModel(Keys.REFRESH_TOKEN_KEY, refresh_token))
                        Success(true)
                    }
                    ?:run {
                        Failure(Throwable("error in saving tokens"))
                    }
            }else{
                Failure(Throwable("unknown error"))
            }
        }catch (t: Throwable){
            when(t){
                is IOException -> Failure(CommonExceptions.ConnectionException)
                else -> Failure(t)
            }
        }
    }

}

typealias LoginWritable = Writable.Suspendable.IO<@JvmSuppressWildcards LoginRequest,
        @JvmSuppressWildcards Response<Boolean,Throwable>>