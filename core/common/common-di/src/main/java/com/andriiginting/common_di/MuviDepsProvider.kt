package com.andriiginting.common_di

interface MuviDepsProvider {
    val appDeps: MuviAppComponent
        get() = provideDeps()

    fun provideDeps(): MuviAppComponent
}