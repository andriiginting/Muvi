package com.andriiginting.home.di

import com.andriiginting.common_di.muviAppDaggerComponent
import com.andriiginting.home.ui.HomeActivity

object MuviHomeInjector {
    fun of(activity: HomeActivity) {
        DaggerMuviHomeComponent.builder()
            .muviAppComponent(activity.muviAppDaggerComponent())
            .build()
            .inject(activity)
    }
}