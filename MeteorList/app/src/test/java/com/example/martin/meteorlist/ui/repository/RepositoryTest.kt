package com.example.martin.meteorlist.ui.repository

import com.example.martin.meteorlist.data.database.MeteoriteDao
import com.example.martin.meteorlist.data.networking.ApiInterface
import com.example.martin.meteorlist.model.Meteorite
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class RepositoryTest {

    @Mock
    private lateinit var mockMeteoriteDao: MeteoriteDao

    @Mock
    private lateinit var mockApiInterface: ApiInterface

    private lateinit var repository: Repository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        repository = Repository(mockApiInterface, mockMeteoriteDao)
    }

    @Test
    fun getMeteorites() {
        val meteoriteList = listOf(METEORITE)

        `when`(mockApiInterface.getMeteorList()).thenReturn(Single.just(meteoriteList))

        val testObserver = TestObserver<List<Meteorite>>()
        repository.getMeteorites()
            .subscribe(testObserver)

        testObserver.assertNoErrors()
        testObserver.assertValue{it -> it[0].id == 1}
    }

    companion object {
        private val METEORITE =
            Meteorite(id = 1, mass = 2.3, name = "Meteorite name", reclat = 2.5, reclong = -3.5, year = "2011")
    }
}