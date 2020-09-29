package com.andriiginting.detail.di

import androidx.lifecycle.ViewModel
import com.andriiginting.common_di.ViewModelKey
import com.andriiginting.detail.data.MuviDetailRepository
import com.andriiginting.detail.data.MuviDetailRepositoryImpl
import com.andriiginting.detail.domain.MuviDetailUseCase
import com.andriiginting.detail.domain.MuviDetailUseCaseImpl
import com.andriiginting.detail.presentation.MuviDetailViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MuviDetailModule {
    @Binds
    abstract fun provideRepository(binds: MuviDetailRepositoryImpl): MuviDetailRepository

    @Binds
    abstract fun provideUseCase(binds: MuviDetailUseCaseImpl): MuviDetailUseCase

    @Binds
    @IntoMap
    @ViewModelKey(MuviDetailViewModel::class)
    abstract fun provideViewModel(binds: MuviDetailViewModel): ViewModel
}