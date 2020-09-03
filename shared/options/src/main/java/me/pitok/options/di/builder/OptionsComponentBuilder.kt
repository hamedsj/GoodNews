package me.pitok.options.di.builder

import me.pitok.dependencyinjection.ComponentBuilder
import me.pitok.options.di.components.DaggerOptionsComponent
import me.pitok.options.di.components.OptionsComponent
import me.pitok.sharedpreferences.di.builder.SharedPreferencesComponentBuilder

object OptionsComponentBuilder: ComponentBuilder<OptionsComponent>(){
    override fun initComponent(): OptionsComponent {
        return DaggerOptionsComponent.builder()
            .sharedPreferencesComponent(SharedPreferencesComponentBuilder.getComponent())
            .build()
    }

}