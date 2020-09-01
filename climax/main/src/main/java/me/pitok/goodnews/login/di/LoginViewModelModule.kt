package me.pitok.goodnews.login.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import me.pitok.dependencyinjection.climax.ClimaxScope
import me.pitok.goodnews.login.LoginViewModel
import me.pitok.lifecycle.ViewModelFactory
import me.pitok.lifecycle.ViewModelKey
import me.pitok.lifecycle.ViewModelProviders

@Module
interface LoginViewModelModule {


    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    @ClimaxScope
    fun bindLoginViewModel(viewModel: LoginViewModel): ViewModel


    companion object {

        @Provides
        @ClimaxScope
        fun provideViewModelFactory(viewModelProviders: ViewModelProviders): ViewModelFactory {
            return ViewModelFactory(viewModelProviders)
        }

    }

}