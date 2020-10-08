package com.andriiginting.muvi.detail

import androidx.lifecycle.Observer
import com.andriiginting.core_network.DetailsMovieData
import com.andriiginting.muvi.detail.domain.MuviDetailUseCase
import com.andriiginting.muvi.detail.presentation.MovieDetailViewState
import com.andriiginting.muvi.detail.presentation.MuviDetailViewModel
import com.andriiginting.uttils.testhelper.InstantRuleExecution
import com.andriiginting.uttils.testhelper.TrampolineSchedulerRX
import com.nhaarman.mockito_kotlin.*
import io.reactivex.Single
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations

class MuviDetailViewModelTest {
    private val useCase: MuviDetailUseCase = mock()
    private lateinit var viewModel: MuviDetailViewModel
    private val observer = mock<Observer<MovieDetailViewState>>()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = MuviDetailViewModel(useCase)

        TrampolineSchedulerRX.start()
        InstantRuleExecution.start()
        viewModel.state.observeForever(observer)
    }

    @Test
    fun `when fetch detail movie and similar success should return list of movie and detail movie`() {
        val id = "123"
        whenever(useCase.getDetailMovies(id))
            .thenReturn(
                Single.just(
                    DetailsMovieData(
                        getSimilarMovieResponse(),
                        getMovieDummyResponse()
                    )
                )
            )

        viewModel.getDetailMovie(id)

        useCase.getDetailMovies(id).test()
            .apply {
                assertNoErrors()
                assertComplete()
                assertValue {
                    it == DetailsMovieData(getSimilarMovieResponse(), getMovieDummyResponse())
                }
            }

        verify(useCase, atLeastOnce()).getDetailMovies(id)
        verify(observer, atLeastOnce()).onChanged(MovieDetailViewState.ShowLoading)
        verify(observer, atLeastOnce()).onChanged(MovieDetailViewState.HideLoading)
        verify(observer, atLeastOnce()).onChanged(
            MovieDetailViewState.GetMovieData(
                getMovieDummyResponse()
            )
        )
        verify(observer, atLeastOnce()).onChanged(
            MovieDetailViewState.GetSimilarMovieData(
                getSimilarMovieResponse()
            )
        )

        verifyNoMoreInteractions(useCase, observer)
        clearInvocations(useCase, observer)
    }

    @Test
    fun `when fetch detail movie and similar return empty should return detail movie`() {
        val id = "123"
        whenever(useCase.getDetailMovies(id))
            .thenReturn(Single.just(DetailsMovieData(emptyList(), getMovieDummyResponse())))

        viewModel.getDetailMovie(id)

        useCase.getDetailMovies(id).test()
            .apply {
                assertNoErrors()
                assertComplete()
                assertValue {
                    it == DetailsMovieData(emptyList(), getMovieDummyResponse())
                }
            }

        verify(useCase, atLeastOnce()).getDetailMovies(id)
        verify(observer, atLeastOnce()).onChanged(MovieDetailViewState.ShowLoading)
        verify(observer, atLeastOnce()).onChanged(MovieDetailViewState.HideLoading)
        verify(observer, atLeastOnce()).onChanged(
            MovieDetailViewState.GetMovieData(
                getMovieDummyResponse()
            )
        )
        verify(observer, atLeastOnce()).onChanged(MovieDetailViewState.SimilarMovieEmpty)

        verifyNoMoreInteractions(useCase, observer)
        clearInvocations(useCase, observer)
    }

    @Test
    fun `when failed fetch detail movie and similar should show error states`() {
        val id = "123"
        val error = Throwable("msg")
        whenever(useCase.getDetailMovies(id))
            .thenReturn(Single.error(error))

        viewModel.getDetailMovie(id)

        useCase.getDetailMovies(id).test()
            .apply {
                assertError(error)
                assertNotComplete()
            }

        verify(useCase, atLeastOnce()).getDetailMovies(id)
        verify(observer, atLeastOnce()).onChanged(MovieDetailViewState.ShowLoading)
        verify(observer, atLeastOnce()).onChanged(MovieDetailViewState.HideLoading)
        verify(observer, atLeastOnce()).onChanged(MovieDetailViewState.GetMovieDataError(error))

        verifyNoMoreInteractions(useCase, observer)
        clearInvocations(useCase, observer)
    }

    @After
    fun tearDown() {
        TrampolineSchedulerRX.tearDown()
        InstantRuleExecution.tearDown()
    }
}