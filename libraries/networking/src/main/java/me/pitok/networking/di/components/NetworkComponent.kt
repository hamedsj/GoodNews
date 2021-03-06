package me.pitok.networking.di.components

import dagger.Component
import me.pitok.androidcore.components.AndroidCoreComponent
import me.pitok.networking.ApiInterface
import me.pitok.networking.AuthorizationInterceptor
import me.pitok.networking.OkHttpAuthenticator
import me.pitok.networking.dataSource.TokenResponseToEntityMapper
import me.pitok.networking.di.modules.NetworkModule
import me.pitok.networking.di.scopes.NetworkScope
import me.pitok.networking.tokenController.TokenReadable
import me.pitok.networking.tokenController.TokenRefresher
import me.pitok.networking.tokenController.TokenWritable
import me.pitok.sharedpreferences.di.components.SharedPreferencesComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@NetworkScope
@Component(modules = [NetworkModule::class],
    dependencies = [
        SharedPreferencesComponent::class,
        AndroidCoreComponent::class
    ])
interface NetworkComponent{

    fun providePkHttpClientCache(): Cache

    fun provideTokenResponseToEntityMapper(): TokenResponseToEntityMapper

    fun provideTokenWriter(): TokenWritable

    fun provideTokenReader(): TokenReadable

    fun provideTokenRefresher(): TokenRefresher

    fun provideOkHttpAuthenticator(): OkHttpAuthenticator

    fun provideAuthorizationInterceptor(): AuthorizationInterceptor

    fun provideOkHttpClient(): OkHttpClient

    fun provideGsonConverter(): GsonConverterFactory

    fun getRetrofit(): Retrofit

    fun getApiInterface(): ApiInterface

}