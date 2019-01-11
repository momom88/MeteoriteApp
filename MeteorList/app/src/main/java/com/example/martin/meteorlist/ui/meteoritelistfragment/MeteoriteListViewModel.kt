package com.example.martin.meteorlist.ui.meteoritelistfragment

import android.util.Log
import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.work.*
import com.example.martin.meteorlist.model.Meteorite
import com.example.martin.meteorlist.ui.repository.Repository
import com.example.martin.meteorlist.utils.AbsentLiveData
import com.example.martin.meteorlist.utils.SchedulerProvider
import io.reactivex.Single
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import com.example.martin.meteorlist.worker.ApiWorker
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.rxkotlin.subscribeBy


class MeteoriteListViewModel @Inject constructor(
    private val repository: Repository,
    private val schedulerProvider: SchedulerProvider
) : ViewModel() {

    private lateinit var allMeteorites: LiveData<List<Meteorite>>
    private var meteoriteError: MutableLiveData<String> = MutableLiveData()

    fun meteoritesFromDatabase(): LiveData<List<Meteorite>> {
        allMeteorites = repository.allMeteorites
        return allMeteorites
    }

    fun scheduleApiWorker() {
        val photoCheckBuilder = PeriodicWorkRequestBuilder<ApiWorker>(1, TimeUnit.DAYS, 3, TimeUnit.HOURS)
        val request = photoCheckBuilder.build()
        WorkManager.getInstance().enqueueUniquePeriodicWork("Api", ExistingPeriodicWorkPolicy.KEEP, request)
    }

    fun meteoriteError(): LiveData<String> {
        return meteoriteError
    }

    fun connectToApi() {
        Log.i("ApiWorker", "connectToApi")
        repository.getMeteorites()
            .compose(schedulerProvider.getSchedulersForSingle())
            .subscribeBy(
                onSuccess = { Log.i("ApiWorker", "ViewModel On Success") },
                onError = { meteoriteError.postValue(it.toString()) }
            )
    }
}
