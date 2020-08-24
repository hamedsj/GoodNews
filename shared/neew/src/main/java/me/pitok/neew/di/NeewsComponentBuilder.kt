package me.pitok.neew.di

import me.pitok.dependencyinjection.ComponentBuilder
import me.pitok.neew.di.components.DaggerNeewsComponent
import me.pitok.neew.di.components.NeewsComponent
import me.pitok.neew.di.modules.NeewApiModule
import me.pitok.networking.di.NetworkComponentBuilder

object NeewsComponentBuilder: ComponentBuilder<NeewsComponent>() {
    override fun initComponent(): NeewsComponent {
        return DaggerNeewsComponent
            .builder()
            .neewApiModule(NeewApiModule())
            .networkComponent(NetworkComponentBuilder.getComponent())
            .build()
    }
}