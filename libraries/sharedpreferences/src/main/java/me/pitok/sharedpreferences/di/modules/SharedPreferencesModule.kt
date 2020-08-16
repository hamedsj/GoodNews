package me.pitok.sharedpreferences.di.modules

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import me.pitok.androidcore.qulifiers.ApplicationContext
import me.pitok.datasource.Readable
import me.pitok.datasource.Writable
import me.pitok.dependencyinjection.library.LibraryScope
import me.pitok.sharedpreferences.Keys
import me.pitok.sharedpreferences.StoreModel
import me.pitok.sharedpreferences.di.qulifiers.TokenSP
import me.pitok.sharedpreferences.tokens.TokenReaderImpl
import me.pitok.sharedpreferences.tokens.TokenWriterImpl

@Module
class SharedPreferencesModule{

    @Provides
    @LibraryScope
    @TokenSP
    fun provideTokenSharedPreferences(@ApplicationContext context: Context): SharedPreferences{
        return context.getSharedPreferences(Keys.TOKENS_SP_NAME,Context.MODE_PRIVATE)
    }

    @Provides
    @LibraryScope
    @TokenSP
    fun provideTokenSharedPreferencesEditor(@TokenSP spToken: SharedPreferences): SharedPreferences.Editor{
        return spToken.edit()
    }

    @Provides
    @LibraryScope
    @TokenSP
    fun provideTokenReaderImpl(@TokenSP spTokens: SharedPreferences): Readable.IO<String, String>{
        return TokenReaderImpl(spTokens)
    }

    @Provides
    @LibraryScope
    @TokenSP
    fun provideTokenWriterImpl(@TokenSP tokensEditor: SharedPreferences.Editor): Writable<StoreModel<String>> {
        return TokenWriterImpl(tokensEditor)
    }

}