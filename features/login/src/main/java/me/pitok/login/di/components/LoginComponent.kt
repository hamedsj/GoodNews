package me.pitok.login.di.components

import dagger.Component
import me.pitok.androidcore.components.AndroidCoreComponent
import me.pitok.coroutines.di.component.CoroutinesComponent
import me.pitok.dependencyinjection.feature.FeatureScope
import me.pitok.login.datasource.LoginWritable
import me.pitok.login.datasource.SignUpWritable
import me.pitok.login.di.modules.LoginApiModule
import me.pitok.login.di.modules.LoginDataSourceModule
import me.pitok.networking.di.components.NetworkComponent
import me.pitok.sharedpreferences.di.components.SharedPreferencesComponent

@FeatureScope
@Component(
    modules = [
        LoginDataSourceModule::class,
        LoginApiModule::class
    ],
    dependencies = [
        CoroutinesComponent::class,
        AndroidCoreComponent::class,
        SharedPreferencesComponent::class,
        NetworkComponent::class
    ]
)
interface LoginComponent {
    fun provideLoginWritable(): LoginWritable

    fun provideSignUpWritable(): SignUpWritable
}