package me.pitok.networking.di.components

import dagger.Component
import me.pitok.dependencyinjection.library.LibraryScope
import me.pitok.networking.ApiInterface
import me.pitok.networking.AuthorizationInterceptor
import me.pitok.networking.OkHttpAuthenticator
import me.pitok.networking.dataSource.TokenResponseToEntityMapper
import me.pitok.networking.di.modules.NetworkModule
import me.pitok.networking.tokenController.TokenReader
import me.pitok.networking.tokenController.TokenRefresher
import me.pitok.networking.tokenController.TokenWriter
import me.pitok.sharedpreferences.di.components.SharedPreferencesComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@LibraryScope
@Component(modules = [NetworkModule::class], dependencies = [SharedPreferencesComponent::class])
interface NetworkComponent{

    fun providePkHttpClientCache(): Cache

    fun provideTokenResponseToEntityMapper(): TokenResponseToEntityMapper

    fun provideTokenWriter(): TokenWriter

    fun provideTokenReader(): TokenReader

    fun provideTokenRefresher(): TokenRefresher

    fun provideOkHttpAuthenticator(): OkHttpAuthenticator

    fun provideAuthorizationInterceptor(): AuthorizationInterceptor

    fun provideOkHttpClient(): OkHttpClient

    fun provideGsonConverter(): GsonConverterFactory

    fun getRetrofit(): Retrofit

    fun getApiInterface(): ApiInterface

}