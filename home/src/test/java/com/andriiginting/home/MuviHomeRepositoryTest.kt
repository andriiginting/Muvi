package com.andriiginting.home

import com.andriiginting.core_network.MovieItem
import com.andriiginting.core_network.MovieResponse
import com.andriiginting.core_network.MuviHomeService
import com.andriiginting.home.data.MuviHomeRepository
import com.andriiginting.home.data.MuviHomeRepositoryImpl
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
        val keys = "keys"
        whenever(service.getPopularMovies(keys))
            .thenReturn(Single.just(response))

        val test = service.getPopularMovies(keys).test()
        repository.getPopularMovie()

        test.apply {
            assertComplete()
            assertNoErrors()
        }

        verify(service, atLeastOnce()).getPopularMovies(keys)
    }

    private var response = MovieResponse(
        mutableListOf(
            MovieItem(
                id = "297761",
                movieId = "",
                posterPath = "/e1mjopzAS2KNsvpbpahQ1a6SkSn.jpg",
                title = "Suicide Squad",
                overview = "From DC Comics comes the Suicide Squad, an antihero team of incarcerated supervillains who act as deniable assets for the United States government, undertaking high-risk black ops missions in exchange for commuted prison sentences.",
                backdropPath = "/ndlQ2Cuc3cjTL7lTynw6I4boP4S.jpg",
                releaseDate = "2016-08-03"
            ),
            MovieItem(
                id = "324668",
                movieId = "",
                posterPath = "/lFSSLTlFozwpaGlO31OoUeirBgQ.jpg",
                title = "Jason Bourne",
                overview = "The most dangerous former operative of the CIA is drawn out of hiding to uncover hidden truths about his past.",
                backdropPath = "/AoT2YrJUJlg5vKE3iMOLvHlTd3m.jpg",
                releaseDate = "2016-07-27"
            )
        )
    )

    @After
    fun tearDown() {
        TrampolineSchedulerRX.tearDown()
    }
}