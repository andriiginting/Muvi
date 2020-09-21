package com.andriiginting.muvi

import android.app.Application
import com.andriiginting.common_di.DaggerMuviAppComponent
import com.andriiginting.common_di.MuviAppComponent
import com.andriiginting.common_di.MuviDepsProvider
import com.andriiginting.core_network.MuviNetworkModule

class App : Application(), MuviDepsProvider {

    open lateinit var appComponent: MuviAppComponent
        protected set

    override fun onCreate() {
        super.onCreate()
        appComponent = muviAppComponent()
        appComponent.inject(this)
    }

    override fun provideDeps(): MuviAppComponent = muviAppComponent()

    private fun muviAppComponent() = DaggerMuviAppComponent.builder()
        .application(this)
        .context(this.applicationContext)
        .networkModule(MuviNetworkModule(BuildConfig.HOST_BASE_URL))
        .build()
}