package me.pitok.sharedpreferences.tokens

import android.content.SharedPreferences
import me.pitok.datasource.Readable
import me.pitok.sharedpreferences.di.qulifiers.TokenSP
import me.pitok.sharedpreferences.typealiases.SpReader

class TokenReaderImpl constructor(@TokenSP private val spTokens: SharedPreferences): SpReader{

    override fun read(input: String): String {
        return requireNotNull(spTokens.getString(input,""))
    }
}