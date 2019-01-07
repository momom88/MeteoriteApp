package com.example.martin.meteorlist.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.martin.meteorlist.ui.ViewModelFactory
import com.example.martin.meteorlist.ui.meteoritelistfragment.MeteoriteListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MeteoriteListViewModel::class)
    abstract fun bindMeteoriteListViewModel(meteoriteListViewModel: MeteoriteListViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}