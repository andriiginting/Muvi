package com.andriiginting.favorite.di

import androidx.lifecycle.ViewModel
import com.andriiginting.common_di.ViewModelKey
import com.andriiginting.favorite.data.MuviFavoriteRepository
import com.andriiginting.favorite.data.MuviFavoriteRepositoryImpl
import com.andriiginting.favorite.domain.MuviFavoriteUseCase
import com.andriiginting.favorite.domain.MuviFavoriteUseCaseImpl
import com.andriiginting.favorite.presentation.MuviFavoriteViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MuviFavoriteModule {

    @Binds
    abstract fun provideRepository(repository: MuviFavoriteRepositoryImpl): MuviFavoriteRepository

    @Binds
    abstract fun provideUseCase(useCaseImpl: MuviFavoriteUseCaseImpl): MuviFavoriteUseCase

    @Binds
    @IntoMap
    @ViewModelKey(MuviFavoriteViewModel::class)
    abstract fun provideViewModel(binds: MuviFavoriteViewModel): ViewModel
}