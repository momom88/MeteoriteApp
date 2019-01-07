package com.example.martin.meteorlist

import android.app.Activity
import android.app.Application
import androidx.work.Configuration
import androidx.work.WorkManager
import com.example.martin.meteorlist.di.AppInjector
import com.example.martin.meteorlist.worker.MeteoriteWorkerFactory
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class MainApp : Application(), HasActivityInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var workerFactory: MeteoriteWorkerFactory

    override fun onCreate() {
        super.onCreate()
        AppInjector.init(this)
        val workManagerConfig = Configuration.Builder().setWorkerFactory(workerFactory).build()
        WorkManager.initialize(this, workManagerConfig)
    }

    override fun activityInjector() = dispatchingAndroidInjector
}