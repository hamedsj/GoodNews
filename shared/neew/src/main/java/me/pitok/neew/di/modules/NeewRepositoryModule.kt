package me.pitok.neew.di.modules

import dagger.Binds
import dagger.Module
import me.pitok.dependencyinjection.shared.SharedScope
import me.pitok.neew.repository.NeewsRepository
import me.pitok.neew.repository.NeewsRepositoryImpl

@Module
interface NeewRepositoryModule {

    @Binds
    @SharedScope
    fun provideNeewRepository(neewsRepositoryImpl: NeewsRepositoryImpl): NeewsRepository

}