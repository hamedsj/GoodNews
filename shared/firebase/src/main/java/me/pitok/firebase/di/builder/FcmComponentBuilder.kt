package me.pitok.firebase.di.builder

import me.pitok.coroutines.di.builder.CoroutinesComponentBuilder
import me.pitok.dependencyinjection.ComponentBuilder
import me.pitok.firebase.di.components.DaggerFcmComponent
import me.pitok.firebase.di.components.FcmComponent
import me.pitok.firebase.di.modules.FcmApiModule
import me.pitok.networking.di.NetworkComponentBuilder
import me.pitok.options.di.builder.OptionsComponentBuilder

object FcmComponentBuilder: ComponentBuilder<FcmComponent>(){
    override fun initComponent(): FcmComponent {
        return DaggerFcmComponent.builder()
            .coroutinesComponent(CoroutinesComponentBuilder.getComponent())
            .networkComponent(NetworkComponentBuilder.getComponent())
            .optionsComponent(OptionsComponentBuilder.getComponent())
            .fcmApiModule(FcmApiModule())
            .build()
    }

}