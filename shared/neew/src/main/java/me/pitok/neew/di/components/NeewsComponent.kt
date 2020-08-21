package me.pitok.neew.di.components

import dagger.Component
import me.pitok.mapper.Mapper
import me.pitok.neew.api.NeewsApiInterface
import me.pitok.neew.api.response.NeewApiEntity
import me.pitok.neew.datasource.NeewWritable
import me.pitok.neew.datasource.NeewsReadable
import me.pitok.neew.datasource.ReportWritable
import me.pitok.neew.di.modules.NeewApiModule
import me.pitok.neew.di.modules.NeewDataSourceModule
import me.pitok.neew.di.modules.NeewMapperModule
import me.pitok.neew.di.modules.NeewRepositoryModule
import me.pitok.neew.entity.NeewEntity
import me.pitok.neew.repository.NeewsRepository
import me.pitok.networking.di.components.NetworkComponent

@Component(modules = [
    NeewApiModule::class,
    NeewRepositoryModule::class,
    NeewMapperModule::class,
    NeewDataSourceModule::class
], dependencies = [NetworkComponent::class])
interface NeewsComponent {

    fun exposeNeewsWritable(): NeewWritable

    fun exposeNeewsReadable(): NeewsReadable

    fun exposeReportWritable(): ReportWritable

    fun exposeNeewMapper(): NeewsRepository

    fun exposeNeewsApiInterface(): NeewsApiInterface

    fun exposeNeewRepository(): Mapper<NeewApiEntity, NeewEntity>
    
}