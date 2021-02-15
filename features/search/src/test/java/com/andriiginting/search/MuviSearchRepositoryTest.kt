package com.andriiginting.search

import com.andriiginting.core_network.MuviSearchService
import com.andriiginting.search.data.MuviSearchRepository
import com.andriiginting.search.data.MuviSearchRepositoryImpl
import com.andriiginting.uttils.testhelper.getDummyResponse
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import org.junit.Before
import org.junit.Test

class MuviSearchRepositoryTest {

    private val service: MuviSearchService = mock()
    private lateinit var repository: MuviSearchRepository

    @Before
    fun setup() {
        repository = MuviSearchRepositoryImpl(service)
    }

    @Test
    fun `when search movie with tittle should return success`() {
        val movieTitle = "Jason Bourne"

        whenever(service.searchMovies(movieTitle))
            .thenReturn(Single.just(getDummyResponse()))

        repository.searchMovie(movieTitle)
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
}