package me.pitok.goodnews.login.di

import dagger.Component
import me.pitok.coroutines.di.component.CoroutinesComponent
import me.pitok.dependencyinjection.climax.ClimaxScope
import me.pitok.goodnews.login.LoginActivity
import me.pitok.lifecycle.ViewModelFactory
import me.pitok.login.di.components.LoginComponent


@ClimaxScope
@Component(
    modules = [LoginViewModelModule::class],
    dependencies = [
        LoginComponent::class,
        CoroutinesComponent::class
    ]
)
interface LoginActivityComponent{

    fun bindLoginViewModel(): ViewModelFactory

    fun inject(loginActivity: LoginActivity)

}