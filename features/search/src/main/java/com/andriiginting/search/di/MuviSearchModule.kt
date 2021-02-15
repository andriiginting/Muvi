package com.andriiginting.search.di

import androidx.lifecycle.ViewModel
import com.andriiginting.common_di.ViewModelKey
import com.andriiginting.search.data.MuviSearchRepository
import com.andriiginting.search.data.MuviSearchRepositoryImpl
import com.andriiginting.search.domain.MuviSearchUseCase
import com.andriiginting.search.domain.MuviSearchUseCaseImpl
import com.andriiginting.search.ui.MuviSearchViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MuviSearchModule {

    @Binds
    abstract fun bindsSearchRepository(repositoryImpl: MuviSearchRepositoryImpl): MuviSearchRepository

    @Binds
    abstract fun bindsSearchUsecase(useCaseImpl: MuviSearchUseCaseImpl): MuviSearchUseCase

    @Binds
    @IntoMap
    @ViewModelKey(MuviSearchViewModel::class)
    abstract fun bindsSearchViewModel(viewModel: MuviSearchViewModel): ViewModel
}