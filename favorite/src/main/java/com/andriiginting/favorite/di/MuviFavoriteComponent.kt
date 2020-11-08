package com.andriiginting.favorite.di

import com.andriiginting.common_di.FeatureScope
import com.andriiginting.common_di.MuviAppComponent
import com.andriiginting.favorite.presentation.FavoriteActivity
import dagger.Component

@FeatureScope
@Component(
    dependencies = [MuviAppComponent::class],
    modules = [MuviFavoriteModule::class]
)
interface MuviFavoriteComponent {
    fun inject(activity: FavoriteActivity)
}