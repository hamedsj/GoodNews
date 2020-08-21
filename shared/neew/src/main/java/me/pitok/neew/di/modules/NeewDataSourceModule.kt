package me.pitok.neew.di.modules

import dagger.Binds
import dagger.Module
import me.pitok.dependencyinjection.shared.SharedScope
import me.pitok.neew.datasource.*

@Module
interface NeewDataSourceModule {

    @Binds
    @SharedScope
    fun provideNeewsWritable(neewsDataSource: NeewsDataSource): NeewWritable

    @Binds
    @SharedScope
    fun provideNeewsReadable(neewsDataSource: NeewsDataSource): NeewsReadable


    @Binds
    @SharedScope
    fun provideReportWritable(reportDataSource: ReportDataSource): ReportWritable

}