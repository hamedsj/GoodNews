package me.pitok.settings.di.builder

import me.pitok.androidcore.builder.AndroidCoreComponentBuilder
import me.pitok.coroutines.di.builder.CoroutinesComponentBuilder
import me.pitok.dependencyinjection.ComponentBuilder
import me.pitok.options.di.builder.OptionsComponentBuilder
import me.pitok.settings.di.components.DaggerSettingsComponent
import me.pitok.settings.di.components.SettingsComponent
import me.pitok.sharedpreferences.di.builder.SharedPreferencesComponentBuilder

object SettingsComponentBuilder: ComponentBuilder<SettingsComponent>(){
    override fun initComponent(): SettingsComponent {
        return DaggerSettingsComponent
            .builder()
            .coroutinesComponent(CoroutinesComponentBuilder.getComponent())
            .sharedPreferencesComponent(SharedPreferencesComponentBuilder.getComponent())
            .androidCoreComponent(AndroidCoreComponentBuilder.getComponent())
            .optionsComponent(OptionsComponentBuilder.getComponent())
            .build()
    }

}