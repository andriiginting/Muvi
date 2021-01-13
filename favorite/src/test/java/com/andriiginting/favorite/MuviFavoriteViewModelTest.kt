package com.andriiginting.favorite

import androidx.lifecycle.Observer
import com.andriiginting.core_network.MovieItem
import com.andriiginting.favorite.domain.MuviFavoriteUseCase
import com.andriiginting.favorite.presentation.FavoriteViewState
import com.andriiginting.favorite.presentation.MuviFavoriteViewModel
import com.andriiginting.uttils.testhelper.InstantRuleExecution
import com.andriiginting.uttils.testhelper.TrampolineSchedulerRX
import com.andriiginting.uttils.testhelper.getMovieDummyResponse
import com.nhaarman.mockito_kotlin.atLeastOnce
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Flowable
import org.junit.After
import org.junit.Before
import org.junit.Test


class MuviFavoriteViewModelTest {
    private val useCase: MuviFavoriteUseCase = mock()
    private lateinit var viewModel: MuviFavoriteViewModel
    private val observer = mock<Observer<FavoriteViewState>>()

    @Before
    fun setup() {
        viewModel = MuviFavoriteViewModel(useCase)
        TrampolineSchedulerRX.start()
        InstantRuleExecution.start()
        viewModel.state.observeForever(observer)
    }

    @Test
    fun `get all favorite movie and should return data`() {
        val data = getMovieDummyResponse()
        val availableMovie = listOf(data)

        whenever(useCase.getAllFavoriteMovie())
            .thenReturn(Flowable.just(availableMovie))

        viewModel.getMovies()

        verify(useCase, atLeastOnce()).getAllFavoriteMovie()
        verify(observer, atLeastOnce()).onChanged(FavoriteViewState.HideLoading)
        verify(observer, atLeastOnce()).onChanged(FavoriteViewState.GetFavoriteMovie(availableMovie))
    }

    @Test
    fun `get all favorite movie and should return empty screen when no available movie`() {
        val availableMovie = emptyList<MovieItem>()

        whenever(useCase.getAllFavoriteMovie())
            .thenReturn(Flowable.just(availableMovie))

        viewModel.getMovies()

        verify(useCase, atLeastOnce()).getAllFavoriteMovie()
        verify(observer, atLeastOnce()).onChanged(FavoriteViewState.HideLoading)
        verify(observer, atLeastOnce()).onChanged(FavoriteViewState.ShowEmptyState)
    }

    @Test
    fun `get all favorite movie and should return error screen`() {
        val error = Throwable("no movie at all")

        whenever(useCase.getAllFavoriteMovie())
            .thenReturn(Flowable.error(error))

        viewModel.getMovies()

        verify(useCase, atLeastOnce()).getAllFavoriteMovie()
        verify(observer, atLeastOnce()).onChanged(FavoriteViewState.HideLoading)
        verify(observer, atLeastOnce()).onChanged(FavoriteViewState.ShowError)
    }


    @After
    fun tearDown() {
        TrampolineSchedulerRX.tearDown()
        InstantRuleExecution.tearDown()
    }
}