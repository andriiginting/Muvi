package com.andriiginting.favorite.domain

import com.andriiginting.common_database.MuviFavorites
import com.andriiginting.core_network.MovieItem
import com.andriiginting.favorite.data.MuviFavoriteRepository
import com.andriiginting.uttils.flowableIo
import io.reactivex.Flowable
import javax.inject.Inject

interface MuviFavoriteUseCase {
    fun getAllFavoriteMovie(): Flowable<List<MovieItem>>
}

class MuviFavoriteUseCaseImpl @Inject constructor(
    private val repository: MuviFavoriteRepository
) : MuviFavoriteUseCase {

    private fun mapToMovieModel(data: List<MuviFavorites>): List<MovieItem> {
        val list: MutableList<MovieItem> = mutableListOf()
        data.forEach { movieItem ->
            val item = MovieItem(
                id = movieItem.movieFavoriteId,
                movieId = movieItem.movieFavoriteId,
                posterPath = movieItem.posterPath,
                overview = movieItem.overview,
                title = movieItem.movieTitle,
                backdropPath = movieItem.backdropPath,
                releaseDate = movieItem.releaseDate
            )
            list.add(item)
        }
        return list
    }

    override fun getAllFavoriteMovie(): Flowable<List<MovieItem>> {
        return repository.getAllFavoriteMovie()
            .map(::mapToMovieModel)
            .compose(flowableIo())
    }
}