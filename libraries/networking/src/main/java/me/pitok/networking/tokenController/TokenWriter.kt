package me.pitok.networking.tokenController

import me.pitok.datasource.Writable
import me.pitok.networking.TokenEntity
import me.pitok.sharedpreferences.Keys
import me.pitok.sharedpreferences.StoreModel

class TokenWriter constructor(private val spTokenStringWriter: Writable<StoreModel<String>>) : TokenWritable {

    override fun write(input: TokenEntity) {
        spTokenStringWriter.write(StoreModel(Keys.ACCESS_TOKEN_KEY, input.accessToken))
        spTokenStringWriter.write(StoreModel(Keys.REFRESH_TOKEN_KEY, input.refreshToken))
    }
}

typealias TokenWritable = Writable<TokenEntity>