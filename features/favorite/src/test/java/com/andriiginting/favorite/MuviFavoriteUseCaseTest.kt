package com.andriiginting.favorite

import com.andriiginting.common_database.MuviFavorites
import com.andriiginting.favorite.data.MuviFavoriteRepository
import com.andriiginting.favorite.domain.MuviFavoriteUseCase
import com.andriiginting.favorite.domain.MuviFavoriteUseCaseImpl
import com.andriiginting.uttils.testhelper.TrampolineSchedulerRX
import com.andriiginting.uttils.testhelper.getMovieDummyResponse
import com.nhaarman.mockito_kotlin.*
import io.reactivex.Flowable
import org.junit.After
import org.junit.Before
import org.junit.Test

class MuviFavoriteUseCaseTest {

    private val repository: MuviFavoriteRepository = mock()
    private lateinit var useCaseTest: MuviFavoriteUseCase

    @Before
    fun setup() {
        useCaseTest = MuviFavoriteUseCaseImpl(repository)
        TrampolineSchedulerRX.start()
    }

    @Test
    fun `get all favorite movie stored on db`() {
        val data = getMovieDummyResponse()
        val listMovie = listOf(getFavoritesDummy())
        whenever(repository.getAllFavoriteMovie())
            .thenReturn(Flowable.just(listMovie))

        val test = useCaseTest.getAllFavoriteMovie().test()

        test.apply {
            assertComplete()
            assertValue {
                it.first() == data
            }
        }

        verify(repository, atLeastOnce()).getAllFavoriteMovie()
    }

    @After
    fun tearDown() {
        TrampolineSchedulerRX.tearDown()
    }

    private fun getFavoritesDummy() = MuviFavorites(
        movieFavoriteId = "324668",
        movieTitle = "Jason Bourne",
        posterPath = "/lFSSLTlFozwpaGlO31OoUeirBgQ.jpg",
        overview = "The most dangerous former operative of the CIA is drawn out of hiding to uncover hidden truths about his past.",
        backdropPath = "/AoT2YrJUJlg5vKE3iMOLvHlTd3m.jpg",
        releaseDate ="2016-07-27"
    )
}