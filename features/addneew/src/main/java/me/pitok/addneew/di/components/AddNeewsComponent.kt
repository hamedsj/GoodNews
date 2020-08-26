package me.pitok.addneew.di.components

import dagger.Component
import me.pitok.addneew.di.modules.AddNeewsViewModelModule
import me.pitok.addneew.views.AddNeewsFragment
import me.pitok.coroutines.di.component.CoroutinesComponent
import me.pitok.dependencyinjection.feature.FeatureScope
import me.pitok.lifecycle.ViewModelFactory
import me.pitok.neew.di.components.NeewsComponent

@FeatureScope
@Component(
    modules = [AddNeewsViewModelModule::class],
    dependencies = [
        CoroutinesComponent::class,
        NeewsComponent::class
    ]
)
interface AddNeewsComponent {
    fun bindNeewsListViewModel(): ViewModelFactory

    fun inject(neewsListFragment: AddNeewsFragment)
}