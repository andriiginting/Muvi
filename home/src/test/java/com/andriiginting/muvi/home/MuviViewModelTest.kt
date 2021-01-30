package com.andriiginting.muvi.home

import androidx.lifecycle.Observer
import com.andriiginting.core_network.MovieResponse
import com.andriiginting.muvi.home.domain.MuviHomeUseCase
import com.andriiginting.muvi.home.ui.HomeViewState
import com.andriiginting.muvi.home.ui.MuviHomeViewModel
import com.andriiginting.uttils.testhelper.*
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
    fun `when fetch popular movie and success should return empty list of movie`() {
        whenever(useCase.getAllMovies())
            .thenReturn(Single.just(getEmptyMovieResponse()))

        viewModel.getMovieData()

        useCase.getAllMovies().test()
            .apply {
                assertNoErrors()
                assertComplete()
                assertValue {
                    it == getEmptyMovieResponse()
                }
            }

        verify(useCase, atLeastOnce()).getAllMovies()
        verify(observer, atLeastOnce()).onChanged(HomeViewState.ShowLoading)
        verify(observer, atLeastOnce()).onChanged(HomeViewState.HideLoading)
        verify(observer, atLeastOnce()).onChanged(HomeViewState.EmptyScreen)

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

    @Test
    fun `when select latest filter category movie and success should return empty list of movie`() {
        val latestMovieFilter = 1
        whenever(useCase.getLatestMovies())
            .thenReturn(Single.just(getEmptyMovieResponse()))

        viewModel.getFilteredData(latestMovieFilter)

        useCase.getLatestMovies().test()
            .apply {
                assertNoErrors()
                assertComplete()
                assertValue {
                    it == getEmptyMovieResponse()
                }
            }

        verify(useCase, atLeastOnce()).getLatestMovies()
        verify(observer, atLeastOnce()).onChanged(HomeViewState.ShowLoading)
        verify(observer, atLeastOnce()).onChanged(HomeViewState.HideLoading)
        verify(observer, atLeastOnce()).onChanged(HomeViewState.EmptyScreen)

        verifyNoMoreInteractions(useCase, observer)
        clearInvocations(useCase, observer)
    }

    @Test
    fun `when select now playing filter category movie and success should return empty list of movie`() {
        val position = 2
        whenever(useCase.getNowPlayingMovies())
            .thenReturn(Single.just(getEmptyMovieResponse()))

        viewModel.getFilteredData(position)

        useCase.getNowPlayingMovies().test()
            .apply {
                assertNoErrors()
                assertComplete()
                assertValue {
                    it == getEmptyMovieResponse()
                }
            }

        verify(useCase, atLeastOnce()).getNowPlayingMovies()
        verify(observer, atLeastOnce()).onChanged(HomeViewState.ShowLoading)
        verify(observer, atLeastOnce()).onChanged(HomeViewState.HideLoading)
        verify(observer, atLeastOnce()).onChanged(HomeViewState.EmptyScreen)

        verifyNoMoreInteractions(useCase, observer)
        clearInvocations(useCase, observer)
    }

    @Test
    fun `when select top rated filter category movie and success should return empty list of movie`() {
        val position = 3
        whenever(useCase.getTopRatedMovies())
            .thenReturn(Single.just(getEmptyMovieResponse()))

        viewModel.getFilteredData(position)

        useCase.getTopRatedMovies().test()
            .apply {
                assertNoErrors()
                assertComplete()
                assertValue {
                    it == getEmptyMovieResponse()
                }
            }

        verify(useCase, atLeastOnce()).getTopRatedMovies()
        verify(observer, atLeastOnce()).onChanged(HomeViewState.ShowLoading)
        verify(observer, atLeastOnce()).onChanged(HomeViewState.HideLoading)
        verify(observer, atLeastOnce()).onChanged(HomeViewState.EmptyScreen)

        verifyNoMoreInteractions(useCase, observer)
        clearInvocations(useCase, observer)
    }

    @Test
    fun `when select upcoming filter category movie and success should return empty list of movie`() {
        val position = 4
        whenever(useCase.getUpcomingMovies())
            .thenReturn(Single.just(getEmptyMovieResponse()))

        viewModel.getFilteredData(position)

        useCase.getUpcomingMovies().test()
            .apply {
                assertNoErrors()
                assertComplete()
                assertValue {
                    it == getEmptyMovieResponse()
                }
            }

        verify(useCase, atLeastOnce()).getUpcomingMovies()
        verify(observer, atLeastOnce()).onChanged(HomeViewState.ShowLoading)
        verify(observer, atLeastOnce()).onChanged(HomeViewState.HideLoading)
        verify(observer, atLeastOnce()).onChanged(HomeViewState.EmptyScreen)

        verifyNoMoreInteractions(useCase, observer)
        clearInvocations(useCase, observer)
    }

    @Test
    fun `when select latest filter category movie and success should return list of movie`() {
        val latestMovieFilter = 1
        whenever(useCase.getLatestMovies())
            .thenReturn(Single.just(getDummyResponse()))

        viewModel.getFilteredData(latestMovieFilter)

        useCase.getLatestMovies().test()
            .apply {
                assertNoErrors()
                assertComplete()
                assertValue {
                    it == load(MovieResponse::class.java, "movie_response.json")
                }
            }

        verify(useCase, atLeastOnce()).getLatestMovies()
        verify(observer, atLeastOnce()).onChanged(HomeViewState.ShowLoading)
        verify(observer, atLeastOnce()).onChanged(HomeViewState.HideLoading)
        verify(observer, atLeastOnce()).onChanged(HomeViewState.GetMovieData(getDummyResponse()))

        verifyNoMoreInteractions(useCase, observer)
        clearInvocations(useCase, observer)
    }

    @Test
    fun `when select now playing filter category movie and success should return list of movie`() {
        val position = 2
        whenever(useCase.getNowPlayingMovies())
            .thenReturn(Single.just(getDummyResponse()))

        viewModel.getFilteredData(position)

        useCase.getNowPlayingMovies().test()
            .apply {
                assertNoErrors()
                assertComplete()
                assertValue {
                    it == load(MovieResponse::class.java, "movie_response.json")
                }
            }

        verify(useCase, atLeastOnce()).getNowPlayingMovies()
        verify(observer, atLeastOnce()).onChanged(HomeViewState.ShowLoading)
        verify(observer, atLeastOnce()).onChanged(HomeViewState.HideLoading)
        verify(observer, atLeastOnce()).onChanged(HomeViewState.GetMovieData(getDummyResponse()))

        verifyNoMoreInteractions(useCase, observer)
        clearInvocations(useCase, observer)
    }

    @Test
    fun `when select top rated filter category movie and success should return list of movie`() {
        val position = 3
        whenever(useCase.getTopRatedMovies())
            .thenReturn(Single.just(getDummyResponse()))

        viewModel.getFilteredData(position)

        useCase.getTopRatedMovies().test()
            .apply {
                assertNoErrors()
                assertComplete()
                assertValue {
                    it == load(MovieResponse::class.java, "movie_response.json")
                }
            }

        verify(useCase, atLeastOnce()).getTopRatedMovies()
        verify(observer, atLeastOnce()).onChanged(HomeViewState.ShowLoading)
        verify(observer, atLeastOnce()).onChanged(HomeViewState.HideLoading)
        verify(observer, atLeastOnce()).onChanged(HomeViewState.GetMovieData(getDummyResponse()))

        verifyNoMoreInteractions(useCase, observer)
        clearInvocations(useCase, observer)
    }

    @Test
    fun `when select upcoming filter category movie and success should return list of movie`() {
        val position = 4
        whenever(useCase.getUpcomingMovies())
            .thenReturn(Single.just(getDummyResponse()))

        viewModel.getFilteredData(position)

        useCase.getUpcomingMovies().test()
            .apply {
                assertNoErrors()
                assertComplete()
                assertValue {
                    it == load(MovieResponse::class.java, "movie_response.json")
                }
            }

        verify(useCase, atLeastOnce()).getUpcomingMovies()
        verify(observer, atLeastOnce()).onChanged(HomeViewState.ShowLoading)
        verify(observer, atLeastOnce()).onChanged(HomeViewState.HideLoading)
        verify(observer, atLeastOnce()).onChanged(HomeViewState.GetMovieData(getDummyResponse()))

        verifyNoMoreInteractions(useCase, observer)
        clearInvocations(useCase, observer)
    }

    @After
    fun tearDown() {
        TrampolineSchedulerRX.tearDown()
        InstantRuleExecution.tearDown()
    }
}