package me.pitok.neew.di.modules

import dagger.Module
import dagger.Provides
import me.pitok.dependencyinjection.shared.SharedScope
import me.pitok.neew.api.NeewsApiInterface
import retrofit2.Retrofit

@Module
class NeewApiModule {

    @Provides
    @SharedScope
    fun provideNeewsApiInterface(retrofit: Retrofit): NeewsApiInterface{
        return retrofit.create(NeewsApiInterface::class.java)
    }

}