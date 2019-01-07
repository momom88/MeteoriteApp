package com.example.martin.meteorlist.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.martin.meteorlist.model.Meteorite

@Dao
interface MeteoriteDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMeteor(meteorite: Meteorite)

    @Query("SELECT * FROM meteorite ORDER BY mass DESC")
    fun queryMeteorites(): LiveData<List<Meteorite>>

}