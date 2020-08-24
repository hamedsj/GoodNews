package me.pitok.neewslist.di.modules

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import me.pitok.dependencyinjection.feature.FeatureScope
import me.pitok.lifecycle.ViewModelFactory
import me.pitok.lifecycle.ViewModelKey
import me.pitok.lifecycle.ViewModelProviders
import me.pitok.neewslist.viewmodels.NeewsListViewModel

@Module
interface NeewsListViewModelModule {


    @Binds
    @IntoMap
    @ViewModelKey(NeewsListViewModel::class)
    @FeatureScope
    fun bindNeewsListViewModel(viewModel: NeewsListViewModel): ViewModel


    companion object {

        @Provides
        @FeatureScope
        fun provideViewModelFactory(viewModelProviders: ViewModelProviders): ViewModelFactory {
            return ViewModelFactory(viewModelProviders)
        }

    }

}