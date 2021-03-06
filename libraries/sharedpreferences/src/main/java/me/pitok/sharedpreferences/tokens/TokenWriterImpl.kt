package me.pitok.sharedpreferences.tokens

import android.content.SharedPreferences
import me.pitok.datasource.Writable
import me.pitok.sharedpreferences.StoreModel
import me.pitok.sharedpreferences.di.qulifiers.TokenSP
import me.pitok.sharedpreferences.typealiases.SpWriter

class TokenWriterImpl constructor(@TokenSP private val tokensEditor: SharedPreferences.Editor): SpWriter{
    override fun write(input: StoreModel<String>) {
        tokensEditor.putString(input.key,input.value).run{
            commit()
        }
    }
}