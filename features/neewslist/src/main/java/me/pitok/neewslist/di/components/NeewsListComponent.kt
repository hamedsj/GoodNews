package me.pitok.neewslist.di.components

import dagger.Component
import me.pitok.androidcore.components.AndroidCoreComponent
import me.pitok.coroutines.di.component.CoroutinesComponent
import me.pitok.dependencyinjection.feature.FeatureScope
import me.pitok.dependencyinjection.shared.SharedScope
import me.pitok.lifecycle.ViewModelFactory
import me.pitok.neew.di.components.NeewsComponent
import me.pitok.neewslist.di.modules.NeewsListViewModelModule
import me.pitok.neewslist.views.NeewListFragment

@FeatureScope
@Component(
    modules = [NeewsListViewModelModule::class],
    dependencies = [
        CoroutinesComponent::class,
        NeewsComponent::class,
        AndroidCoreComponent::class
    ]
)
interface NeewsListComponent {
    fun bindNeewsListViewModel(): ViewModelFactory

    fun inject(neewsListFragment: NeewListFragment)
}