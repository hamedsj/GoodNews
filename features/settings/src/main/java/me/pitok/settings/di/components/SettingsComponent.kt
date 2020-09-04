package me.pitok.settings.di.components

import dagger.Component
import me.pitok.androidcore.components.AndroidCoreComponent
import me.pitok.coroutines.di.component.CoroutinesComponent
import me.pitok.dependencyinjection.feature.FeatureScope
import me.pitok.lifecycle.ViewModelFactory
import me.pitok.options.di.components.OptionsComponent
import me.pitok.settings.di.modules.SettingsViewModelModule
import me.pitok.settings.views.SettingsFragment
import me.pitok.sharedpreferences.di.components.SharedPreferencesComponent

@FeatureScope
@Component(
    modules = [SettingsViewModelModule::class],
    dependencies = [
        CoroutinesComponent::class,
        SharedPreferencesComponent::class,
        AndroidCoreComponent::class,
        OptionsComponent::class
    ]
)
interface SettingsComponent {
    fun bindSettingsViewModel(): ViewModelFactory

    fun inject(settingsFragment: SettingsFragment)
}