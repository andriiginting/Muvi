package com.andriiginting.muvi.home

import com.andriiginting.core_network.MuviHomeService
import com.andriiginting.muvi.home.data.MuviHomeRepository
import com.andriiginting.muvi.home.data.MuviHomeRepositoryImpl
import com.andriiginting.uttils.testhelper.TrampolineSchedulerRX
import com.andriiginting.uttils.testhelper.getDummyResponse
import com.nhaarman.mockito_kotlin.atLeastOnce
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import org.junit.After
import org.junit.Before
import org.junit.Test

class MuviHomeRepositoryTest {

    private val service: MuviHomeService = mock()

    lateinit var repository: MuviHomeRepository

    @Before
    fun setUp() {
        repository = MuviHomeRepositoryImpl(service)
        TrampolineSchedulerRX.start()
    }

    @Test
    fun `when get popular movie, should return list of movie from service`() {
        whenever(service.getPopularMovies())
            .thenReturn(Single.just(getDummyResponse()))

        val test = service.getPopularMovies().test()
        repository.getPopularMovie()

        test.apply {
            assertComplete()
            assertNoErrors()
        }

        verify(service, atLeastOnce()).getPopularMovies()
    }

    @Test
    fun `when get latest movie, should return list of movie from service`() {
        whenever(service.getLatestMovies())
            .thenReturn(Single.just(getDummyResponse()))

        val test = service.getLatestMovies().test()
        repository.getLatestMovies()

        test.apply {
            assertComplete()
            assertNoErrors()
        }

        verify(service, atLeastOnce()).getLatestMovies()
    }

    @Test
    fun `when get now playing movie, should return list of movie from service`() {
        whenever(service.getNowPlayingMovies())
            .thenReturn(Single.just(getDummyResponse()))

        val test = service.getNowPlayingMovies().test()
        repository.getNowPlayingMovies()

        test.apply {
            assertComplete()
            assertNoErrors()
        }

        verify(service, atLeastOnce()).getNowPlayingMovies()
    }

    @Test
    fun `when get top rated movie, should return list of movie from service`() {
        whenever(service.getTopRatedMovies())
            .thenReturn(Single.just(getDummyResponse()))

        val test = service.getTopRatedMovies().test()
        repository.getTopRatedMovies()

        test.apply {
            assertComplete()
            assertNoErrors()
        }

        verify(service, atLeastOnce()).getTopRatedMovies()
    }

    @Test
    fun `when get upcoming movie, should return list of movie from service`() {
        whenever(service.getUpcomingMovies())
            .thenReturn(Single.just(getDummyResponse()))

        val test = service.getUpcomingMovies().test()
        repository.getUpcomingMovies()

        test.apply {
            assertComplete()
            assertNoErrors()
        }

        verify(service, atLeastOnce()).getUpcomingMovies()
    }

    @After
    fun tearDown() {
        TrampolineSchedulerRX.tearDown()
    }
}