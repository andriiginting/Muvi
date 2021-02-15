package com.andriiginting.search.di

import com.andriiginting.common_di.muviAppDaggerComponent
import com.andriiginting.search.ui.MuviSearchActivity

object MuviSearchInjector {
    fun of(activity: MuviSearchActivity) {
        DaggerMuviSearchComponent.builder()
            .muviAppComponent(activity.muviAppDaggerComponent())
            .build()
            .inject(activity)
    }
}