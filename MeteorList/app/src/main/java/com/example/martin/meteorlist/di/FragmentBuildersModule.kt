package com.example.martin.meteorlist.di

import com.example.martin.meteorlist.ui.mapsdetailfragment.MapsDetailFragment
import com.example.martin.meteorlist.ui.meteoritelistfragment.MeteoriteListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeMapsDetailFragment(): MapsDetailFragment

    @ContributesAndroidInjector
    abstract fun contributeMeteorListFragment(): MeteoriteListFragment

}