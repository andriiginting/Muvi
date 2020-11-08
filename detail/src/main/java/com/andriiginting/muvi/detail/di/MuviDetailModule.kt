package com.andriiginting.muvi.detail.di

import androidx.lifecycle.ViewModel
import com.andriiginting.common_database.MuviDatabase
import com.andriiginting.common_database.MuviFavoriteDAO
import com.andriiginting.common_di.ViewModelKey
import com.andriiginting.muvi.detail.data.MuviDetailRepository
import com.andriiginting.muvi.detail.data.MuviDetailRepositoryImpl
import com.andriiginting.muvi.detail.domain.MuviDetailMapper
import com.andriiginting.muvi.detail.domain.MuviDetailMapperImpl
import com.andriiginting.muvi.detail.domain.MuviDetailUseCase
import com.andriiginting.muvi.detail.domain.MuviDetailUseCaseImpl
import com.andriiginting.muvi.detail.presentation.MuviDetailViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
abstract class MuviDetailModule {
    @Binds
    abstract fun provideRepository(binds: MuviDetailRepositoryImpl): MuviDetailRepository

    @Binds
    abstract fun provideMapper(binds: MuviDetailMapperImpl): MuviDetailMapper

    @Binds
    abstract fun provideUseCase(binds: MuviDetailUseCaseImpl): MuviDetailUseCase

    @Binds
    @IntoMap
    @ViewModelKey(MuviDetailViewModel::class)
    abstract fun provideViewModel(binds: MuviDetailViewModel): ViewModel
}