package me.pitok.sharedpreferences.di.builder

import me.pitok.androidcore.builder.AndroidCoreComponentBuilder
import me.pitok.dependencyinjection.ComponentBuilder
import me.pitok.sharedpreferences.di.components.DaggerSharedPreferencesComponent
import me.pitok.sharedpreferences.di.components.SharedPreferencesComponent

object SharedPreferencesComponentBuilder: ComponentBuilder<SharedPreferencesComponent>() {
    override fun initComponent(): SharedPreferencesComponent {
        return DaggerSharedPreferencesComponent
            .builder()
            .androidCoreComponent(AndroidCoreComponentBuilder.getComponent())
            .build()
    }
}