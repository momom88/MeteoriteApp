package com.example.martin.meteorlist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.martin.meteorlist.data.database.AppDatabase
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import com.example.martin.meteorlist.LiveDataTestUtil.getValue
import com.example.martin.meteorlist.model.Meteorite
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Test the implementation of [MeteoriteDaoTest]
 */
@RunWith(AndroidJUnit4::class)
class MeteoriteDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: AppDatabase

    @Before
    fun initDb() {
        // using an in-memory database because the information stored here disappears after test
        database = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().targetContext,
            AppDatabase::class.java
        )
            // allowing main thread queries, just for testing
            .allowMainThreadQueries()
            .build()
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun insertMeteoriteAndRead() {
        database.weatherDao().insertMeteor(METEORITE)
        val list = getValue(database.weatherDao().queryMeteorites())
        assertThat(list, notNullValue())
        assertThat(list[0].id, `is`(1))
    }

    companion object {
        private val METEORITE =
            Meteorite(id = 1, mass = 2.3, name = "Meteorite name", reclat = 2.5, reclong = -3.5, year = "2011")
    }
}
