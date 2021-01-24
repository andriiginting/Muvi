package com.andriiginting.muvi.detail

import com.andriiginting.core_network.DetailsMovieData
import com.andriiginting.muvi.detail.data.MuviDetailRepository
import com.andriiginting.muvi.detail.domain.MuviDetailMapper
import com.andriiginting.muvi.detail.domain.MuviDetailUseCase
import com.andriiginting.muvi.detail.domain.MuviDetailUseCaseImpl
import com.andriiginting.uttils.testhelper.TrampolineSchedulerRX
import com.nhaarman.mockito_kotlin.atLeastOnce
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Maybe
import io.reactivex.Single
import org.junit.After
import org.junit.Before
import org.junit.Test

class MuviDetailUseCaseTest {
    private val repository: MuviDetailRepository = mock()
    private val mapper: MuviDetailMapper = mock()
    private lateinit var useCase: MuviDetailUseCase

    @Before
    fun setUp() {
        useCase = MuviDetailUseCaseImpl(repository, mapper)
        TrampolineSchedulerRX.start()
    }

    @Test
    fun `should return similar movie response when call detail repository`() {
        val id = "297761"
        whenever(repository.getSimilarMovie(id))
            .thenReturn(Single.just(getDummyResponse()))
        whenever(repository.getDetailMovie(id))
            .thenReturn(Single.just(getMovieDummyResponse()))

        useCase.getDetailMovies(id)

        repository.getSimilarMovie(id).test().apply {
            assertComplete()
            assertNoErrors()
            assertTerminated()
        }

        repository.getDetailMovie(id).test().apply {
            assertComplete()
            assertNoErrors()
            assertTerminated()
        }

        useCase.getDetailMovies(id).test()
            .assertValue {
                it == DetailsMovieData(getSimilarMovieResponse(), getMovieDummyResponse())
            }

        verify(repository, atLeastOnce()).getSimilarMovie(id)
        verify(repository, atLeastOnce()).getDetailMovie(id)
    }

    @Test
    fun `when want to store to database should return success`(){
        val id = 1.toLong()
        whenever(mapper.mapToMuviFavorite(getMovieDummyResponse()))
            .thenReturn(getFavoritesDummy())
        whenever(repository.storeToDatabase(getFavoritesDummy()))
            .thenReturn(Single.just(id))

        val test = useCase.storeToDatabase(getMovieDummyResponse()).test()

        test.apply {
            assertComplete()
            assertNoErrors()
            assertTerminated()
        }

        verify(mapper, atLeastOnce()).mapToMuviFavorite(getMovieDummyResponse())
        verify(repository, atLeastOnce()).storeToDatabase(getFavoritesDummy())
    }

    @Test
    fun `when want to remove from database should return success`(){
        val id = "123"
        whenever(repository.removeFromDatabase(id))
            .thenReturn(Single.just(Unit))

        val test = useCase.removeFromDatabase(id).test()

        test.apply {
            assertComplete()
            assertNoErrors()
            assertTerminated()
        }

        verify(repository, atLeastOnce()).removeFromDatabase(id)
    }

    @Test
    fun `when want to check db whether data exist or not should return success`(){
        val id = 123
        whenever(repository.isFavoriteMovie(id))
            .thenReturn(Maybe.just(getFavoritesDummy()))
        whenever(mapper.mapToMovieItem(getFavoritesDummy()))
            .thenReturn(getMovieDummyResponse())

        val test = useCase.checkFavoriteMovie(id.toString()).test()

        test.apply {
            assertComplete()
            assertNoErrors()
            assertTerminated()
        }

        verify(repository, atLeastOnce()).isFavoriteMovie(id)
        verify(mapper, atLeastOnce()).mapToMovieItem(getFavoritesDummy())
    }

    @After
    fun tearDown() {
        TrampolineSchedulerRX.tearDown()
    }
}