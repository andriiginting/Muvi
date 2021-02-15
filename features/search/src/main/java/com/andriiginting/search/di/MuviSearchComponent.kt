package com.andriiginting.search.di

import com.andriiginting.common_di.FeatureScope
import com.andriiginting.common_di.MuviAppComponent
import com.andriiginting.search.ui.MuviSearchActivity
import dagger.Component

@FeatureScope
@Component(
    dependencies = [MuviAppComponent::class],
    modules = [MuviSearchModule::class]
)
interface MuviSearchComponent {
    fun inject(activity: MuviSearchActivity)
}