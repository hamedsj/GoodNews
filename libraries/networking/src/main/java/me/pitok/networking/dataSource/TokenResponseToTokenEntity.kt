package me.pitok.networking.dataSource

import me.pitok.mapper.Mapper
import me.pitok.networking.TokenEntity
import me.pitok.networking.response.TokenResponse

class TokenResponseToTokenEntity : Mapper<TokenResponse,TokenEntity> {
    override fun mapFirstToSecond(first: TokenResponse): TokenEntity {
        return TokenEntity(first.data.accessToken,
            first.data.refreshToken
        )
    }
}

typealias TokenResponseToEntityMapper = Mapper<TokenResponse,TokenEntity>