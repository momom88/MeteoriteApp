package com.example.martin.meteorlist.worker

import android.content.Context
import android.util.Log
import androidx.work.RxWorker
import androidx.work.WorkerParameters
import com.example.martin.meteorlist.data.database.MeteoriteDao
import com.example.martin.meteorlist.data.networking.ApiInterface
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import io.reactivex.Single


class ApiWorker @AssistedInject constructor(
    @Assisted private val params: WorkerParameters,
    @Assisted private val context: Context,
    private val apiInterface: ApiInterface,
    private val meteoriteDao: MeteoriteDao
) : RxWorker(context, params) {
    override fun createWork(): Single<Result> {
        return apiInterface.getMeteorList()
            .doOnSuccess {
                for (item in it) {
                    meteoriteDao.insertMeteor(item)
                    Log.i("ApiWorker", "doOnSuccess ${item.name}")
                }
            }.map { Result.success() }
            .onErrorReturn { Result.failure() }
    }

    @AssistedInject.Factory
    interface Factory : ChildWorkerFactory
}
