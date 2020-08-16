package me.pitok.sharedpreferences.di.components

import android.content.SharedPreferences
import dagger.Component
import me.pitok.androidcore.components.AndroidCoreComponent
import me.pitok.datasource.Readable
import me.pitok.datasource.Writable
import me.pitok.dependencyinjection.library.LibraryScope
import me.pitok.sharedpreferences.StoreModel
import me.pitok.sharedpreferences.di.modules.SharedPreferencesModule
import me.pitok.sharedpreferences.di.qulifiers.TokenSP

@LibraryScope
@Component(modules = [SharedPreferencesModule::class], dependencies = [AndroidCoreComponent::class])
interface SharedPreferencesComponent {

    @TokenSP
    fun provideTokenSharedPreferences(): SharedPreferences

    @TokenSP
    fun provideTokenSharedPreferencesEditor(): SharedPreferences.Editor

    @TokenSP
    fun provideTokenReaderImpl(): Readable.IO<String, String>

    @TokenSP
    fun provideTokenWriterImpl(): Writable<StoreModel<String>>
}