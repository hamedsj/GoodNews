package me.pitok.login.di.modules

import dagger.Binds
import dagger.Module
import me.pitok.dependencyinjection.feature.FeatureScope
import me.pitok.login.datasource.LoginDataSource
import me.pitok.login.datasource.LoginWritable
import me.pitok.login.datasource.SignUpDataSource
import me.pitok.login.datasource.SignUpWritable

@Module
interface LoginDataSourceModule {

    @Binds
    @FeatureScope
    fun provideLoginWriter(loginDataSource: LoginDataSource): LoginWritable

    @Binds
    @FeatureScope
    fun provideSignUpWriter(signUpDataSource: SignUpDataSource): SignUpWritable

}