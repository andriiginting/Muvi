package com.andriiginting.search.ui

import com.andriiginting.base_ui.MuviBaseViewModel
import com.andriiginting.core_network.MovieItem
import com.andriiginting.search.domain.MuviSearchUseCase
import javax.inject.Inject

class MuviSearchViewModel @Inject constructor(
    private val useCase: MuviSearchUseCase
) : MuviBaseViewModel<MuviSearchViewState>() {

    fun initialView() {
        _state.value = MuviSearchViewState.EmptyScreen
    }

    fun submittedQuery(query: String) {
        _state.value = MuviSearchViewState.HideKeyboard
        useCase.getMovieFrom(query)
            .doOnSubscribe {
                _state.postValue(MuviSearchViewState.ShowLoading)
            }
            .doAfterTerminate { _state.postValue(MuviSearchViewState.HideLoading) }
            .subscribe({ data ->
                handleSuccess(data = data.resultsIntent)
            }, { error ->
                _state.postValue(MuviSearchViewState.GetMovieDataError(error))
            })
            .let(addDisposable::add)
    }

    fun onSearchTextCleared() {
        _state.value = MuviSearchViewState.ClearText
        _state.value = MuviSearchViewState.EmptyScreen
    }

    private fun handleSuccess(data: List<MovieItem>) {
        if (data.isEmpty()) {
            _state.value = MuviSearchViewState.EmptyScreen
        } else {
            _state.value = MuviSearchViewState.GetMovieData(data)
        }
    }
}

sealed class MuviSearchViewState {
    object ShowLoading : MuviSearchViewState()
    object HideLoading : MuviSearchViewState()
    object ClearText : MuviSearchViewState()
    object EmptyScreen : MuviSearchViewState()
    object HideKeyboard : MuviSearchViewState()

    data class GetMovieData(val data: List<MovieItem>) : MuviSearchViewState()
    data class GetMovieDataError(val error: Throwable) : MuviSearchViewState()
}