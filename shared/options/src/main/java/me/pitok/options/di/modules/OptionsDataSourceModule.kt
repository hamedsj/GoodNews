package me.pitok.options.di.modules

import dagger.Binds
import dagger.Module
import dagger.Provides
import me.pitok.datasource.Readable
import me.pitok.dependencyinjection.shared.SharedScope
import me.pitok.options.datasource.NotifsCountReader
import me.pitok.options.datasource.UIReader
import me.pitok.options.entities.NotifsCount
import me.pitok.options.entities.UIMode

@Module
interface OptionsDataSourceModule {

    @Binds
    @SharedScope
    fun provideUIReadable(uiDataSource: UIReader): Readable<UIMode>

    @Binds
    @SharedScope
    fun provideNotifsCountReadable(notifsCountDataSource: NotifsCountReader): Readable<NotifsCount>

    companion object{
        @Provides
        @SharedScope
        fun provideStartupTask(uiSettingReader: Readable<UIMode>) = Runnable {
            UIMode.currentUIMode = uiSettingReader.read()
        }
    }


}