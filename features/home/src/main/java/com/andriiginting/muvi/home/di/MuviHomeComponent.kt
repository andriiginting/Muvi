package com.andriiginting.muvi.home.di

import com.andriiginting.common_di.FeatureScope
import com.andriiginting.common_di.MuviAppComponent
import com.andriiginting.muvi.home.ui.HomeActivity
import dagger.Component

@FeatureScope
@Component(
    dependencies = [MuviAppComponent::class],
    modules = [MuviHomeModule::class]
)
interface MuviHomeComponent {
    fun inject(inject: HomeActivity)
}