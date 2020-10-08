package com.andriiginting.muvi.detail.domain

import com.andriiginting.core_network.DetailsMovieData
import com.andriiginting.muvi.detail.data.MuviDetailRepository
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import javax.inject.Inject

interface MuviDetailUseCase {
    fun getDetailMovies(movieId: String): Single<DetailsMovieData>
}

class MuviDetailUseCaseImpl @Inject constructor(
    private val repository: MuviDetailRepository
) : MuviDetailUseCase {

    override fun getDetailMovies(movieId: String): Single<DetailsMovieData> {
        return Single.zip(
            repository.getDetailMovie(movieId),
            repository.getSimilarMovie(movieId).map { it.resultsIntent },
            BiFunction { movieItem, similarMovies ->
                DetailsMovieData(similarMovies, movieItem)
            }
        )
    }
}