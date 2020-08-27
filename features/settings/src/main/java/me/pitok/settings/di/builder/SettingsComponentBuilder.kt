package me.pitok.settings.di.builder

import me.pitok.coroutines.di.builder.CoroutinesComponentBuilder
import me.pitok.dependencyinjection.ComponentBuilder
import me.pitok.settings.di.components.DaggerSettingsComponent
import me.pitok.settings.di.components.SettingsComponent

object SettingsComponentBuilder: ComponentBuilder<SettingsComponent>(){
    override fun initComponent(): SettingsComponent {
        return DaggerSettingsComponent
            .builder()
            .coroutinesComponent(CoroutinesComponentBuilder.getComponent())
            .build()
    }

}