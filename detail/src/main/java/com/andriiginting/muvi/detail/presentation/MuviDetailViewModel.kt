package com.andriiginting.muvi.detail.presentation

import android.util.Log
import com.andriiginting.base_ui.MuviBaseViewModel
import com.andriiginting.core_network.DetailsMovieData
import com.andriiginting.core_network.MovieItem
import com.andriiginting.muvi.detail.domain.MuviDetailUseCase
import com.andriiginting.uttils.singleIo
import timber.log.Timber
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

    fun storeFavoriteMovie(movieItem: MovieItem) {
        useCase.storeToDatabase(movieItem)
            .subscribe({
                Log.e("muvi-database", "successw to store favorite movie")
                _state.value = MovieDetailViewState.StoredFavoriteMovie
            }, { error ->
                Timber.e(error, "failed to store favorite movie")
                Log.e("muvi-database", "failed to store favorite movie")
                _state.value = MovieDetailViewState.FailedStoreFavoriteMovie
            })
            .let(addDisposable::add)
    }

    fun removeFavoriteMovie(movieId: String) {
        useCase.removeFromDatabase(movieId)
            .subscribe({
                _state.value = MovieDetailViewState.RemovedFavoriteMovie
            }, { error ->
                Timber.e(error, "failed to remove favorite movie")
                _state.value = MovieDetailViewState.FailedRemoveFavoriteMovie
            })
            .let(addDisposable::add)
    }

    fun checkFavoriteMovie(movieId: String) {
        useCase.checkFavoriteMovie(movieId)
            .subscribe({ data ->
                if (data != null) {
                    _state.value = MovieDetailViewState.FavoriteMovie(true)
                }
            }, { error ->
                Timber.e(error, "failed to remove favorite movie")
                _state.value = MovieDetailViewState.FavoriteMovie(false)
            })
            .let(addDisposable::add)
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
    object StoredFavoriteMovie : MovieDetailViewState()
    object FailedStoreFavoriteMovie : MovieDetailViewState()
    object RemovedFavoriteMovie : MovieDetailViewState()
    object FailedRemoveFavoriteMovie : MovieDetailViewState()

    data class GetMovieData(val data: MovieItem) : MovieDetailViewState()
    data class GetSimilarMovieData(val data: List<MovieItem>) : MovieDetailViewState()
    data class GetMovieDataError(val error: Throwable) : MovieDetailViewState()
    data class FavoriteMovie(val isFavorite: Boolean) : MovieDetailViewState()
}