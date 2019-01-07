package com.example.martin.meteorlist.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.martin.meteorlist.model.Meteorite

@Database(entities = [Meteorite::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase(){
    abstract fun weatherDao(): MeteoriteDao
}