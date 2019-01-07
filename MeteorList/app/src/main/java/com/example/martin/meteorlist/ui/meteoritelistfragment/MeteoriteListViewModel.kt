package com.example.martin.meteorlist.ui.meteoritelistfragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.work.*
import com.example.martin.meteorlist.model.Meteorite
import com.example.martin.meteorlist.ui.repository.Repository
import com.example.martin.meteorlist.utils.SchedulerProvider
import io.reactivex.Single
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import com.example.martin.meteorlist.worker.ApiWorker


class MeteoriteListViewModel @Inject constructor(
    private val repository: Repository,
    private val schedulerProvider: SchedulerProvider
) : ViewModel() {

    lateinit var allMeteorites: LiveData<List<Meteorite>>

    fun getMeteorites(): Single<List<Meteorite>> = repository.getMeteorites()
        .compose(schedulerProvider.getSchedulersForSingle())

    fun meteoritesFromDatabase(): LiveData<List<Meteorite>> {
        allMeteorites = repository.allMeteorites
        return allMeteorites
    }

    fun scheduleApiWorker() {
        val photoCheckBuilder = PeriodicWorkRequestBuilder<ApiWorker>(1, TimeUnit.DAYS, 3, TimeUnit.HOURS)
        val request = photoCheckBuilder.build()
        WorkManager.getInstance().enqueueUniquePeriodicWork("Api", ExistingPeriodicWorkPolicy.KEEP, request)
    }
}
