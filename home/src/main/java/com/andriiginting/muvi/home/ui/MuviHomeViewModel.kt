package com.andriiginting.muvi.home.ui

import android.util.Log
import com.andriiginting.base_ui.MuviBaseViewModel
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
                _state.postValue(HomeViewState.GetMovieData(data))
            }, { error ->
                _state.value = HomeViewState.GetMovieDataError(error)
            }).let(addDisposable::add)
    }
}

sealed class HomeViewState {
    object ShowLoading : HomeViewState()
    object HideLoading : HomeViewState()

    data class GetMovieData(val data: MovieResponse) : HomeViewState()
    data class GetMovieDataError(val error: Throwable) : HomeViewState()
}