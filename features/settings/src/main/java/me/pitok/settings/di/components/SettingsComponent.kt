package me.pitok.settings.di.components

import android.app.Activity
import dagger.Component
import me.pitok.androidcore.components.AndroidCoreComponent
import me.pitok.coroutines.di.component.CoroutinesComponent
import me.pitok.datasource.Readable
import me.pitok.dependencyinjection.feature.FeatureScope
import me.pitok.lifecycle.ViewModelFactory
import me.pitok.settings.di.modules.SettingsDataSourceModule
import me.pitok.settings.di.modules.SettingsViewModelModule
import me.pitok.settings.entity.NotifsCount
import me.pitok.settings.entity.UIMode
import me.pitok.settings.views.SettingsFragment
import me.pitok.sharedpreferences.di.components.SharedPreferencesComponent

@FeatureScope
@Component(
    modules = [
        SettingsViewModelModule::class,
        SettingsDataSourceModule::class
    ],
    dependencies = [
        CoroutinesComponent::class,
        SharedPreferencesComponent::class,
        AndroidCoreComponent::class
    ]
)
interface SettingsComponent {
    fun bindSettingsViewModel(): ViewModelFactory

    fun provideUIReadable(): Readable<UIMode>

    fun provideNotifsCountReadable(): Readable<NotifsCount>

    fun provideStartupTask(): Runnable

    fun inject(settingsFragment: SettingsFragment)
}