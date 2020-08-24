package me.pitok.neewslist.di.builder

import me.pitok.coroutines.di.builder.CoroutinesComponentBuilder
import me.pitok.dependencyinjection.ComponentBuilder
import me.pitok.neew.di.NeewsComponentBuilder
import me.pitok.neewslist.di.components.DaggerNeewsListComponent
import me.pitok.neewslist.di.components.NeewsListComponent

object NeewsListComponentBuilder: ComponentBuilder<NeewsListComponent>(){
    override fun initComponent(): NeewsListComponent {
        return DaggerNeewsListComponent
            .builder()
            .coroutinesComponent(CoroutinesComponentBuilder.getComponent())
            .neewsComponent(NeewsComponentBuilder.getComponent())
            .build()
    }

}