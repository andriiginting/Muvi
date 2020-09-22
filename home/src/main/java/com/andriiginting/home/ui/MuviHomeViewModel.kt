package com.andriiginting.home.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.andriiginting.base_ui.MuviBaseViewModel
import com.andriiginting.core_network.MovieResponse
import com.andriiginting.home.domain.MuviHomeUseCase
import javax.inject.Inject

class MuviHomeViewModel @Inject constructor(
    private val useCase: MuviHomeUseCase
) : MuviBaseViewModel() {

    private val _state: MutableLiveData<HomeViewState> = MutableLiveData()
    val state: LiveData<HomeViewState>
        get() = _state

    fun getMovieData() {
        useCase.getAllMovies()
            .doOnSubscribe { _state.value = HomeViewState.ShowLoading }
            .doAfterTerminate { _state.value = HomeViewState.HideLoading }
            .subscribe({ data ->
                _state.postValue(HomeViewState.GetMovieData(data))
                Log.d("muvi-fetch", data.resultsIntent[0].backdropPath)
            }, { error ->
                _state.value = HomeViewState.GetMovieDataError(error)
                Log.d("muvi-fetch", "${error.message}")
            }).let(addDisposable::add)
    }
}

sealed class HomeViewState {
    object ShowLoading : HomeViewState()
    object HideLoading : HomeViewState()

    data class GetMovieData(val data: MovieResponse) : HomeViewState()
    data class GetMovieDataError(val error: Throwable) : HomeViewState()
}