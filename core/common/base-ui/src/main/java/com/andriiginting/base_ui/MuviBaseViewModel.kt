package com.andriiginting.base_ui

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

open class MuviBaseViewModel : ViewModel() {
    val addDisposable by lazy { CompositeDisposable() }

    override fun onCleared() {
        addDisposable.clear()
    }
}