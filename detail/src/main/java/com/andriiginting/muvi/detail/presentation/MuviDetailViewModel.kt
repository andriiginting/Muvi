package com.andriiginting.muvi.detail.presentation

import com.andriiginting.base_ui.MuviBaseViewModel
import com.andriiginting.core_network.MovieItem
import com.andriiginting.muvi.detail.domain.MuviDetailUseCase
import javax.inject.Inject

class MuviDetailViewModel @Inject constructor(
    private val useCase: MuviDetailUseCase
) : MuviBaseViewModel<MovieDetailViewState>() {

    fun getSimilarMovie(movieId: String) {
        useCase.getSimilarMovie(movieId)
            .doOnSubscribe { _state.value = MovieDetailViewState.ShowLoading }
            .doAfterTerminate { _state.value = MovieDetailViewState.HideLoading }
            .subscribe({ data ->
                _state.postValue(MovieDetailViewState.GetSimilarMovieData(data))
            }, { error ->
                _state.value = MovieDetailViewState.GetSimilarMovieDataError(error)
            }).let(addDisposable::add)
    }
}

sealed class MovieDetailViewState {
    object ShowLoading : MovieDetailViewState()
    object HideLoading : MovieDetailViewState()

    data class GetSimilarMovieData(val data: List<MovieItem>) : MovieDetailViewState()
    data class GetSimilarMovieDataError(val error: Throwable) : MovieDetailViewState()
}