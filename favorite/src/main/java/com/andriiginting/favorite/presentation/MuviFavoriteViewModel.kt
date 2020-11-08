package com.andriiginting.favorite.presentation

import android.util.Log
import com.andriiginting.base_ui.MuviBaseViewModel
import com.andriiginting.core_network.MovieItem
import com.andriiginting.favorite.domain.MuviFavoriteUseCase
import javax.inject.Inject

class MuviFavoriteViewModel @Inject constructor(
    private val useCase: MuviFavoriteUseCase
) : MuviBaseViewModel<FavoriteViewState>() {

    fun getMovies() {
        useCase.getAllFavoriteMovie()
            .doOnSubscribe { _state.value = FavoriteViewState.ShowLoading }
            .doAfterTerminate { _state.value = FavoriteViewState.HideLoading }
            .subscribe({ data ->
                Log.d("favorite", data.toString())
                if (data.isEmpty()) {
                    _state.postValue(FavoriteViewState.ShowEmptyState)
                } else {
                    _state.postValue(FavoriteViewState.GetFavoriteMovie(data))
                }
            }, {
                Log.d("favorite", it.message.toString())
                _state.value = FavoriteViewState.ShowError
            }).let(addDisposable::add)
    }
}

sealed class FavoriteViewState {
    object ShowLoading : FavoriteViewState()
    object HideLoading : FavoriteViewState()
    object ShowError : FavoriteViewState()
    object ShowEmptyState : FavoriteViewState()

    data class GetFavoriteMovie(val data: List<MovieItem>) : FavoriteViewState()
}