package me.pitok.networking.tokenController

import me.pitok.datasource.Readable
import me.pitok.networking.TokenEntity
import me.pitok.sharedpreferences.Keys

class TokenReader constructor(private val spTokenReader: Readable.IO<String, String>) : TokenReadable{
    override fun read(): TokenEntity {
        return TokenEntity(spTokenReader.read(Keys.ACCESS_TOKEN_KEY),
            spTokenReader.read(Keys.REFRESH_TOKEN_KEY))
    }

}

typealias TokenReadable = Readable<TokenEntity>