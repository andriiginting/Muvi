package com.andriiginting.home.domain

import com.andriiginting.core_network.MovieResponse
import com.andriiginting.home.data.MuviHomeRepository
import com.andriiginting.uttils.singleIo
import io.reactivex.Single
import javax.inject.Inject

interface MuviHomeUseCase {
    fun getAllMovies(): Single<MovieResponse>
}

class MuviHomeUseCaseImpl @Inject constructor(
    private val repository: MuviHomeRepository
): MuviHomeUseCase {

    override fun getAllMovies(): Single<MovieResponse> {
        return repository.getPopularMovie()
            .compose(singleIo())
    }
}