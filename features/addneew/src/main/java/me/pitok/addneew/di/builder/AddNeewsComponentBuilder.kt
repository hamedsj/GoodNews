package me.pitok.addneew.di.builder

import me.pitok.addneew.di.components.AddNeewsComponent
import me.pitok.addneew.di.components.DaggerAddNeewsComponent
import me.pitok.coroutines.di.builder.CoroutinesComponentBuilder
import me.pitok.dependencyinjection.ComponentBuilder
import me.pitok.neew.di.NeewsComponentBuilder

object AddNeewsComponentBuilder: ComponentBuilder<AddNeewsComponent>(){
    override fun initComponent(): AddNeewsComponent {
        return DaggerAddNeewsComponent
            .builder()
            .coroutinesComponent(CoroutinesComponentBuilder.getComponent())
            .neewsComponent(NeewsComponentBuilder.getComponent())
            .build()
    }

}