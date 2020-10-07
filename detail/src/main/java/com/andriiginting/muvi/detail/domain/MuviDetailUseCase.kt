package com.andriiginting.muvi.detail.domain

import com.andriiginting.core_network.MovieItem
import com.andriiginting.muvi.detail.data.MuviDetailRepository
import com.andriiginting.uttils.singleIo
import io.reactivex.Single
import javax.inject.Inject

interface MuviDetailUseCase {
    fun getSimilarMovie(movieId: String): Single<List<MovieItem>>
}

class MuviDetailUseCaseImpl @Inject constructor(
    private val repository: MuviDetailRepository
) : MuviDetailUseCase {

    override fun getSimilarMovie(movieId: String): Single<List<MovieItem>> {
        return repository.getSimilarMovie(movieId)
            .map { it.resultsIntent }
            .compose(singleIo())
    }
}