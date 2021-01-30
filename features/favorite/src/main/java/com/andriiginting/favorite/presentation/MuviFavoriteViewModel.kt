package com.andriiginting.favorite.presentation

import com.andriiginting.base_ui.MuviBaseViewModel
import com.andriiginting.core_network.MovieItem
import com.andriiginting.favorite.domain.MuviFavoriteUseCase
import javax.inject.Inject

class MuviFavoriteViewModel @Inject constructor(
    private val useCase: MuviFavoriteUseCase
) : MuviBaseViewModel<FavoriteViewState>() {

    fun getMovies() {
        useCase.getAllFavoriteMovie()
            .doAfterTerminate { _state.value = FavoriteViewState.HideLoading }
            .subscribe({ data ->
                if (data.isEmpty()) {
                    _state.postValue(FavoriteViewState.ShowEmptyState)
                } else {
                    _state.postValue(FavoriteViewState.GetFavoriteMovie(data))
                }
            }, {
                _state.value = FavoriteViewState.HideLoading
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