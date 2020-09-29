package com.andriiginting.detail.di

import com.andriiginting.common_di.muviAppDaggerComponent
import com.andriiginting.detail.MuviDetailActivity

object MuviDetailInjector {
    fun of(activity: MuviDetailActivity) {
        DaggerMuviDetailComponent.builder()
            .muviAppComponent(activity.muviAppDaggerComponent())
            .build()
            .inject(activity)
    }
}