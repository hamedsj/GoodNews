package me.pitok.login.di.builder

import me.pitok.androidcore.builder.AndroidCoreComponentBuilder
import me.pitok.coroutines.di.builder.CoroutinesComponentBuilder
import me.pitok.dependencyinjection.ComponentBuilder
import me.pitok.login.di.components.DaggerLoginComponent
import me.pitok.login.di.components.LoginComponent
import me.pitok.login.di.modules.LoginApiModule
import me.pitok.networking.di.NetworkComponentBuilder
import me.pitok.sharedpreferences.di.builder.SharedPreferencesComponentBuilder

object LoginComponentBuilder: ComponentBuilder<LoginComponent>(){
    override fun initComponent(): LoginComponent {
        return DaggerLoginComponent
            .builder()
            .coroutinesComponent(CoroutinesComponentBuilder.getComponent())
            .androidCoreComponent(AndroidCoreComponentBuilder.getComponent())
            .networkComponent(NetworkComponentBuilder.getComponent())
            .sharedPreferencesComponent(SharedPreferencesComponentBuilder.getComponent())
            .loginApiModule(LoginApiModule())
            .build()
    }

}