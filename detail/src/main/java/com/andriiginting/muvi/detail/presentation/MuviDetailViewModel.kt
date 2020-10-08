package com.andriiginting.muvi.detail.presentation

import com.andriiginting.base_ui.MuviBaseViewModel
import com.andriiginting.core_network.DetailsMovieData
import com.andriiginting.core_network.MovieItem
import com.andriiginting.muvi.detail.domain.MuviDetailUseCase
import com.andriiginting.uttils.singleIo
import javax.inject.Inject

class MuviDetailViewModel @Inject constructor(
    private val useCase: MuviDetailUseCase
) : MuviBaseViewModel<MovieDetailViewState>() {

    fun getDetailMovie(movieId: String) {
        useCase.getDetailMovies(movieId)
            .doOnSubscribe { _state.postValue(MovieDetailViewState.ShowLoading) }
            .doAfterTerminate { _state.postValue(MovieDetailViewState.HideLoading) }
            .compose(singleIo())
            .subscribe({ data ->
                _state.value = MovieDetailViewState.GetMovieData(data.movie)
                handleSimilarMovieData(data)
            }, { error ->
                _state.value = MovieDetailViewState.GetMovieDataError(error)
            }).let(addDisposable::add)
    }

    private fun handleSimilarMovieData(data: DetailsMovieData) {
        if (data.similarMovies.isEmpty()) {
            _state.postValue(MovieDetailViewState.SimilarMovieEmpty)
        } else {
            _state.postValue(MovieDetailViewState.GetSimilarMovieData(data.similarMovies))
        }
    }
}

sealed class MovieDetailViewState {
    object ShowLoading : MovieDetailViewState()
    object HideLoading : MovieDetailViewState()
    object SimilarMovieEmpty : MovieDetailViewState()

    data class GetMovieData(val data: MovieItem) : MovieDetailViewState()
    data class GetSimilarMovieData(val data: List<MovieItem>) : MovieDetailViewState()
    data class GetMovieDataError(val error: Throwable) : MovieDetailViewState()
}