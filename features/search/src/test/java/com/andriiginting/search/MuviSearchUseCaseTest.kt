package com.andriiginting.search

import com.andriiginting.search.data.MuviSearchRepository
import com.andriiginting.search.domain.MuviSearchUseCase
import com.andriiginting.search.domain.MuviSearchUseCaseImpl
import com.andriiginting.uttils.testhelper.TrampolineSchedulerRX
import com.andriiginting.uttils.testhelper.getDummyResponse
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import org.junit.After
import org.junit.Before
import org.junit.Test

class MuviSearchUseCaseTest {

    private val repository: MuviSearchRepository = mock()
    private lateinit var useCase: MuviSearchUseCase

    @Before
    fun setup() {
        useCase = MuviSearchUseCaseImpl(repository)
        TrampolineSchedulerRX.start()
    }

    @Test
    fun `should return movie item when search movie with jason as query`() {
        val movieTitle = "Jason Bourne"

        whenever(repository.searchMovie(movieTitle))
            .thenReturn(Single.just(getDummyResponse()))

        useCase.getMovieFrom(movieTitle)
            .test()
            .apply {
                assertNoErrors()
                assertComplete()
                assertTerminated()
                assertValue {
                    it == getDummyResponse()
                }
            }
    }

    @After
    fun tearDown() {
        TrampolineSchedulerRX.tearDown()
    }
}