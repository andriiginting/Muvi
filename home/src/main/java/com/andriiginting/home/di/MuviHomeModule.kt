package com.andriiginting.home.di

import androidx.lifecycle.ViewModel
import com.andriiginting.common_di.ViewModelKey
import com.andriiginting.home.data.MuviHomeRepository
import com.andriiginting.home.data.MuviHomeRepositoryImpl
import com.andriiginting.home.domain.MuviHomeUseCase
import com.andriiginting.home.domain.MuviHomeUseCaseImpl
import com.andriiginting.home.ui.MuviHomeViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MuviHomeModule {

    @Binds
    abstract fun provideRepository(binds: MuviHomeRepositoryImpl): MuviHomeRepository

    @Binds
    abstract fun provideUseCase(binds: MuviHomeUseCaseImpl): MuviHomeUseCase

    @Binds
    @IntoMap
    @ViewModelKey(MuviHomeViewModel::class)
    abstract fun provideViewModel(binds: MuviHomeViewModel): ViewModel
}