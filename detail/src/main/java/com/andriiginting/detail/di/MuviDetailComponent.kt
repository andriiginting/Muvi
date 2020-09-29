package com.andriiginting.detail.di

import com.andriiginting.common_di.FeatureScope
import com.andriiginting.common_di.MuviAppComponent
import com.andriiginting.detail.MuviDetailActivity
import dagger.Component

@FeatureScope
@Component(
    dependencies = [MuviAppComponent::class],
    modules = [MuviDetailModule::class]
)
interface MuviDetailComponent {
    fun inject(inject: MuviDetailActivity)
}