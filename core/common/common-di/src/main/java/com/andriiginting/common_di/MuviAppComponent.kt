package com.andriiginting.common_di

import android.app.Application
import android.content.Context
import com.andriiginting.common_database.MuviDatabaseModule
import com.andriiginting.core_network.MuviNetworkModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        MuviNetworkModule::class,
        MuviDatabaseModule::class
    ]
)
interface MuviAppComponent : MuviAppDeps {

    fun inject(application: Application)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder

        @BindsInstance
        fun application(application: Application): Builder

        fun networkModule(networkModule: MuviNetworkModule): Builder

        fun databaseModule(databaseModule: MuviDatabaseModule): Builder

        fun build(): MuviAppComponent
    }
}