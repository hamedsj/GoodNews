package me.pitok.login.di.modules

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import me.pitok.dependencyinjection.feature.FeatureScope
import me.pitok.lifecycle.ViewModelFactory
import me.pitok.lifecycle.ViewModelKey
import me.pitok.lifecycle.ViewModelProviders
import me.pitok.login.viewmodels.LoginViewModel

@Module
interface LoginViewModelModule {


    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    @FeatureScope
    fun bindNeewsListViewModel(viewModel: LoginViewModel): ViewModel


    companion object {

        @Provides
        @FeatureScope
        fun provideViewModelFactory(viewModelProviders: ViewModelProviders): ViewModelFactory {
            return ViewModelFactory(viewModelProviders)
        }

    }

}