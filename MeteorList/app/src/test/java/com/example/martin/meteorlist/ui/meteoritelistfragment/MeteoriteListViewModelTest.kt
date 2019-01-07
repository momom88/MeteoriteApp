package com.example.martin.meteorlist.ui.meteoritelistfragment

import com.example.martin.meteorlist.model.Meteorite
import com.example.martin.meteorlist.ui.repository.Repository
import com.example.martin.meteorlist.utils.SchedulerProvider
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class MeteoriteListViewModelTest {

    @Mock
    private lateinit var mockRepository: Repository

    private val schedulerProvider = SchedulerProvider(Schedulers.trampoline(), Schedulers.trampoline())

    private lateinit var meteoriteListViewModel: MeteoriteListViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        meteoriteListViewModel = MeteoriteListViewModel(mockRepository, schedulerProvider)
    }

    @Test
    fun getMeteorites() {
        val meteoriteList = listOf(METEORITE)
        Mockito.`when`(mockRepository.getMeteorites()).thenReturn(Single.just(meteoriteList))

        val testObserve = TestObserver<List<Meteorite>>()
        meteoriteListViewModel.getMeteorites()
            .subscribe(testObserve)

        testObserve.assertNoErrors()
        testObserve.assertValue{it -> it[0].id == 1}
    }

    companion object {
        private val METEORITE =
            Meteorite(id = 1, mass = 2.3, name = "Meteorite name", reclat = 2.5, reclong = -3.5, year = "2011")
    }
}