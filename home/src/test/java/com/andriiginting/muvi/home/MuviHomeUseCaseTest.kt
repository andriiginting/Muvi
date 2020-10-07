package com.andriiginting.muvi.home

import com.andriiginting.core_network.MovieResponse
import com.andriiginting.muvi.home.data.MuviHomeRepository
import com.andriiginting.muvi.home.domain.MuviHomeUseCase
import com.andriiginting.muvi.home.domain.MuviHomeUseCaseImpl
import com.andriiginting.uttils.testhelper.TrampolineSchedulerRX
import com.andriiginting.uttils.testhelper.getDummyResponse
import com.andriiginting.uttils.testhelper.load
import com.nhaarman.mockito_kotlin.atLeastOnce
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import org.junit.After
import org.junit.Before
import org.junit.Test

class MuviHomeUseCaseTest {
    private val repository: MuviHomeRepository = mock()

    lateinit var useCase: MuviHomeUseCase

    @Before
    fun setUp() {
        useCase = MuviHomeUseCaseImpl(repository)
        TrampolineSchedulerRX.start()
    }

    @Test
    fun `should return movie response when call homerepository`() {
        whenever(repository.getPopularMovie())
            .thenReturn(Single.just(getDummyResponse()))

        useCase.getAllMovies()

        repository.getPopularMovie().test().apply {
            assertComplete()
            assertNoErrors()
            assertValue {
                it == load(
                    MovieResponse::class.java,
                    "movie_response.json"
                )
            }
        }

        verify(repository, atLeastOnce()).getPopularMovie()
    }


    @After
    fun tearDown() {
        TrampolineSchedulerRX.tearDown()
    }
}