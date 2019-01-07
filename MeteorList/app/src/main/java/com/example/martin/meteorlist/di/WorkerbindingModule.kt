package com.example.martin.meteorlist.di

import android.content.Context
import com.example.martin.meteorlist.MainApp
import com.example.martin.meteorlist.worker.ApiWorker
import com.example.martin.meteorlist.worker.ChildWorkerFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class WorkerbindingModule {
    @Binds
    @IntoMap
    @WorkerKey(ApiWorker::class)
    abstract fun bindApiWorker(factory: ApiWorker.Factory): ChildWorkerFactory

    @Binds
    abstract fun bindContext(app: MainApp): Context
}