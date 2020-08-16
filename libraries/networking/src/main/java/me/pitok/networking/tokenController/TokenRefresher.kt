package me.pitok.networking.tokenController

import me.pitok.datasource.Readable
import me.pitok.networking.*
import me.pitok.networking.dataSource.TokenResponseToEntityMapper
import me.pitok.sharedpreferences.Keys

class TokenRefresher constructor(private val apiInterface: ApiInterface,
                                 private val spTokenReader: Readable.IO<String,String>,
                                 private val mapper: TokenResponseToEntityMapper) : Readable<TokenEntity> {
    override fun read(): TokenEntity {
        return apiInterface.refreshToken(spTokenReader.read(Keys.REFRESH_TOKEN_KEY)).run {
            if (isSuccessful()) {
                mapper.mapFirstToSecond(getSuccessResponse().value)
            }else {
                throw getFailureResponse().error
            }
        }
    }
}