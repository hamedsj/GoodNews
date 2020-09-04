package me.pitok.goodnews.login.di

import me.pitok.coroutines.di.builder.CoroutinesComponentBuilder
import me.pitok.dependencyinjection.ComponentBuilder
import me.pitok.login.di.builder.LoginComponentBuilder

object LoginActivityComponentBuilder: ComponentBuilder<LoginActivityComponent>(){
    override fun initComponent(): LoginActivityComponent {
        return DaggerLoginActivityComponent
            .builder()
            .loginComponent(LoginComponentBuilder.getComponent())
            .coroutinesComponent(CoroutinesComponentBuilder.getComponent())
            .build()
    }
}