package me.pitok.neew.di.modules

import dagger.Binds
import dagger.Module
import me.pitok.dependencyinjection.shared.SharedScope
import me.pitok.mapper.Mapper
import me.pitok.neew.api.response.NeewApiEntity
import me.pitok.neew.entity.NeewEntity
import me.pitok.neew.mapper.NeewApiEntityToNeewEntityMapper

@Module
interface NeewMapperModule {

    @Binds
    @SharedScope
    fun provideNeewMapper(neewMapper: NeewApiEntityToNeewEntityMapper): Mapper<NeewApiEntity, NeewEntity>

}