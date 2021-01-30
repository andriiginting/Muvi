package com.andriiginting.muvi.detail.di

import com.andriiginting.common_di.muviAppDaggerComponent
import com.andriiginting.muvi.detail.MuviDetailActivity

object MuviDetailInjector {
    fun of(activity: MuviDetailActivity) {
        DaggerMuviDetailComponent.builder()
            .muviAppComponent(activity.muviAppDaggerComponent())
            .build()
            .inject(activity)
    }
}