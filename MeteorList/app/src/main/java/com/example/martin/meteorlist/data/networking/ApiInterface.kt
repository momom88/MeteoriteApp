package com.example.martin.meteorlist.data.networking

import com.example.martin.meteorlist.model.Meteorite
import io.reactivex.Single
import retrofit2.http.GET

interface ApiInterface {
    @GET("resource/y77d-th95.json?$\nwhere=fall%20=%20%27Fell%27%20AND%20year%20>=%20%272011-01-01T00:00:00%27")
    fun getMeteorList(): Single<List<Meteorite>>
}