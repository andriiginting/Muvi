package com.andriiginting.muvi.home.ui

import com.andriiginting.base_ui.MuviBaseViewModel
import com.andriiginting.core_network.HomeBannerData
import com.andriiginting.core_network.MovieResponse
import com.andriiginting.muvi.home.domain.MuviHomeUseCase
import javax.inject.Inject

class MuviHomeViewModel @Inject constructor(
    private val useCase: MuviHomeUseCase
) : MuviBaseViewModel<HomeViewState>() {

    fun getMovieData() {
        useCase.getAllMovies()
            .doOnSubscribe { _state.value = HomeViewState.ShowLoading }
            .doAfterTerminate { _state.value = HomeViewState.HideLoading }
            .subscribe({ data ->
                handleDataSuccess(data)
            }, { error ->
                _state.value = HomeViewState.GetMovieDataError(error)
            }).let(addDisposable::add)
    }

    fun getFilteredData(position: Int) {
        when (position) {
            ALL_MOVIE_POSITION -> getMovieData()
            LATEST_MOVIE_POSITION -> getLatestMovieData()
            NOW_PLAYING_MOVIE_POSITION -> getNowPlayingMovieData()
            TOP_RATED_MOVIE_POSITION -> getTopRatedMovieData()
            UPCOMING_MOVIE_DATA_POSITION -> getUpcomingMovieData()
            else -> getMovieData()
        }
    }

    fun getHomeBanner() {
        useCase.getHomeBanner()
            .doOnSubscribe { _state.value = HomeViewState.ShowLoading }
            .doAfterTerminate { _state.value = HomeViewState.HideLoading }
            .subscribe({ data ->
                _state.postValue(HomeViewState.GetHomeBannerData(data))
            }, {
                _state.value = HomeViewState.BannerError
            }).let(addDisposable::add)
    }

    private fun getLatestMovieData() {
        useCase.getLatestMovies()
            .doOnSubscribe { _state.value = HomeViewState.ShowLoading }
            .doAfterTerminate { _state.value = HomeViewState.HideLoading }
            .subscribe({ data ->
                handleDataSuccess(data)
            }, { error ->
                _state.value = HomeViewState.GetMovieDataError(error)
            }).let(addDisposable::add)
    }

    private fun getNowPlayingMovieData() {
        useCase.getNowPlayingMovies()
            .doOnSubscribe { _state.value = HomeViewState.ShowLoading }
            .doAfterTerminate { _state.value = HomeViewState.HideLoading }
            .subscribe({ data ->
                handleDataSuccess(data)
            }, { error ->
                _state.value = HomeViewState.GetMovieDataError(error)
            }).let(addDisposable::add)
    }

    private fun getTopRatedMovieData() {
        useCase.getTopRatedMovies()
            .doOnSubscribe { _state.value = HomeViewState.ShowLoading }
            .doAfterTerminate { _state.value = HomeViewState.HideLoading }
            .subscribe({ data ->
                handleDataSuccess(data)
            }, { error ->
                _state.value = HomeViewState.GetMovieDataError(error)
            }).let(addDisposable::add)
    }

    private fun getUpcomingMovieData() {
        useCase.getUpcomingMovies()
            .doOnSubscribe { _state.value = HomeViewState.ShowLoading }
            .doAfterTerminate { _state.value = HomeViewState.HideLoading }
            .subscribe({ data ->
                handleDataSuccess(data)
            }, { error ->
                _state.value = HomeViewState.GetMovieDataError(error)
            }).let(addDisposable::add)
    }

    private fun handleDataSuccess(data: MovieResponse) {
        if (data.resultsIntent.isEmpty()) {
            _state.value = HomeViewState.EmptyScreen
        } else {
            _state.postValue(HomeViewState.GetMovieData(data))
        }
    }

    companion object {
        private const val ALL_MOVIE_POSITION = 0
        private const val LATEST_MOVIE_POSITION = 1
        private const val NOW_PLAYING_MOVIE_POSITION = 2
        private const val TOP_RATED_MOVIE_POSITION = 3
        private const val UPCOMING_MOVIE_DATA_POSITION = 4
    }
}

sealed class HomeViewState {
    object ShowLoading : HomeViewState()
    object HideLoading : HomeViewState()
    object EmptyScreen: HomeViewState()
    object BannerError: HomeViewState()

    data class GetMovieData(val data: MovieResponse) : HomeViewState()
    data class GetHomeBannerData(val data: HomeBannerData) : HomeViewState()
    data class GetMovieDataError(val error: Throwable) : HomeViewState()
}