package com.andriiginting.muvi.detail.domain

import com.andriiginting.core_network.DetailsMovieData
import com.andriiginting.core_network.MovieItem
import com.andriiginting.muvi.detail.data.MuviDetailRepository
import com.andriiginting.uttils.singleIo
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import javax.inject.Inject

interface MuviDetailUseCase {
    fun getSimilarMovie(movieId: String): Single<List<MovieItem>>
    fun getDetailMovie(movieId: String): Single<MovieItem>
    fun getDetailMovies(movieId: String): Single<DetailsMovieData>
}

class MuviDetailUseCaseImpl @Inject constructor(
    private val repository: MuviDetailRepository
) : MuviDetailUseCase {

    override fun getSimilarMovie(movieId: String): Single<List<MovieItem>> {
        return repository.getSimilarMovie(movieId)
            .map { it.resultsIntent }
            .compose(singleIo())
    }

    override fun getDetailMovie(movieId: String): Single<MovieItem> {
        return repository.getDetailMovie(movieId)
            .compose(singleIo())
    }

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