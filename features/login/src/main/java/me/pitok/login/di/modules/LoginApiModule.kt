package me.pitok.login.di.modules

import dagger.Module
import dagger.Provides
import me.pitok.dependencyinjection.feature.FeatureScope
import me.pitok.login.api.LoginApiInterface
import retrofit2.Retrofit

@Module
class LoginApiModule {

    @Provides
    @FeatureScope
    fun provideNeewsApiInterface(retrofit: Retrofit): LoginApiInterface{
        return retrofit.create(LoginApiInterface::class.java)
    }

}