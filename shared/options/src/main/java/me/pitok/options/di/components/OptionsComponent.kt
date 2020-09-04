package me.pitok.options.di.components

import dagger.Component
import me.pitok.datasource.Readable
import me.pitok.dependencyinjection.shared.SharedScope
import me.pitok.options.di.modules.OptionsDataSourceModule
import me.pitok.options.entities.NotifsCount
import me.pitok.options.entities.UIMode
import me.pitok.sharedpreferences.di.components.SharedPreferencesComponent


@SharedScope
@Component(
    modules = [OptionsDataSourceModule::class],
    dependencies = [SharedPreferencesComponent::class]
)
interface OptionsComponent {

    fun exposeUIReadable(): Readable<UIMode>

    fun exposeNotifsCountReadable(): Readable<NotifsCount>

    fun exposeStartupTask(): Runnable

}