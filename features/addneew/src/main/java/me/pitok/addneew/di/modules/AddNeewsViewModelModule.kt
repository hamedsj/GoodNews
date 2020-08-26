package me.pitok.addneew.di.modules

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import me.pitok.addneew.viewmodels.AddNeewsViewModel
import me.pitok.dependencyinjection.feature.FeatureScope
import me.pitok.lifecycle.ViewModelFactory
import me.pitok.lifecycle.ViewModelKey
import me.pitok.lifecycle.ViewModelProviders

@Module
interface AddNeewsViewModelModule {


    @Binds
    @IntoMap
    @ViewModelKey(AddNeewsViewModel::class)
    @FeatureScope
    fun bindNeewsListViewModel(viewModel: AddNeewsViewModel): ViewModel


    companion object {

        @Provides
        @FeatureScope
        fun provideViewModelFactory(viewModelProviders: ViewModelProviders): ViewModelFactory {
            return ViewModelFactory(viewModelProviders)
        }

    }

}