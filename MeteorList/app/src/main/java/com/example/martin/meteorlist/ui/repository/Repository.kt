package com.example.martin.meteorlist.ui.repository

import androidx.lifecycle.LiveData
import com.example.martin.meteorlist.data.database.MeteoriteDao
import com.example.martin.meteorlist.data.networking.ApiInterface
import com.example.martin.meteorlist.model.Meteorite
import io.reactivex.Single
import javax.inject.Inject

class Repository @Inject constructor(
    private val apiInterface: ApiInterface,
    private val meteoriteDao: MeteoriteDao
) {

    val allMeteorites: LiveData<List<Meteorite>> = meteoriteDao.queryMeteorites()

    fun getMeteorites(): Single<List<Meteorite>> {
        return apiInterface.getMeteorList()
            .doOnSuccess {
                for (item in it) {
                    meteoriteDao.insertMeteor(item)
                }
            }
    }
}