package com.andriiginting.home

import androidx.lifecycle.Observer
import com.andriiginting.core_network.MovieResponse
import com.andriiginting.home.domain.MuviHomeUseCase
import com.andriiginting.uttils.testhelper.InstantRuleExecution
import com.andriiginting.uttils.testhelper.TrampolineSchedulerRX
import com.andriiginting.uttils.testhelper.getDummyResponse
import com.andriiginting.uttils.testhelper.load
import com.andriiginting.home.ui.HomeViewState
import com.andriiginting.home.ui.MuviHomeViewModel
import com.nhaarman.mockito_kotlin.*
import io.reactivex.Single
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations

class MuviViewModelTest {
    private val useCase: MuviHomeUseCase = mock()
    private lateinit var viewModel: MuviHomeViewModel
    private val observer = mock<Observer<HomeViewState>>()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = MuviHomeViewModel(useCase)

        TrampolineSchedulerRX.start()
        InstantRuleExecution.start()
        viewModel.state.observeForever(observer)
    }

    @Test
    fun `when fetch popular movie and success should return list of movie`() {
        whenever(useCase.getAllMovies())
            .thenReturn(Single.just(getDummyResponse()))

        viewModel.getMovieData()

        useCase.getAllMovies().test()
            .apply {
                assertNoErrors()
                assertComplete()
                assertValue {
                    it == load(MovieResponse::class.java, "movie_response.json")
                }
            }

        verify(useCase, atLeastOnce()).getAllMovies()
        verify(observer, atLeastOnce()).onChanged(HomeViewState.ShowLoading)
        verify(observer, atLeastOnce()).onChanged(HomeViewState.HideLoading)
        verify(observer, atLeastOnce()).onChanged(HomeViewState.GetMovieData(getDummyResponse()))

        verifyNoMoreInteractions(useCase, observer)
        clearInvocations(useCase, observer)
    }

    @Test
    fun `when fetch popular movie and failed should show error state`() {
        val error = Throwable("error msg")
        whenever(useCase.getAllMovies())
            .thenReturn(Single.error(error))

        viewModel.getMovieData()

        useCase.getAllMovies().test()
            .apply {
                assertNotComplete()
                assertError(error)
            }

        verify(useCase, atLeastOnce()).getAllMovies()
        verify(observer, atLeastOnce()).onChanged(HomeViewState.ShowLoading)
        verify(observer, atLeastOnce()).onChanged(HomeViewState.HideLoading)
        verify(observer, atLeastOnce()).onChanged(HomeViewState.GetMovieDataError(error))

        verifyNoMoreInteractions(useCase, observer)
        clearInvocations(useCase, observer)
    }

    @After
    fun tearDown() {
        TrampolineSchedulerRX.tearDown()
        InstantRuleExecution.tearDown()
    }
}