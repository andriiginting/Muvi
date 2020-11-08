package com.andriiginting.favorite.di

import com.andriiginting.common_di.muviAppDaggerComponent
import com.andriiginting.favorite.presentation.FavoriteActivity

object MuviFavoriteInjector {
    fun of(activity: FavoriteActivity) {
        DaggerMuviFavoriteComponent.builder()
            .muviAppComponent(activity.muviAppDaggerComponent())
            .build()
            .inject(activity)
    }
}