package com.andriiginting.favorite.domain

import com.andriiginting.common_database.MuviFavorites
import com.andriiginting.core_network.MovieItem
import com.andriiginting.favorite.data.MuviFavoriteRepository
import com.andriiginting.uttils.singleIo
import io.reactivex.Single
import javax.inject.Inject

interface MuviFavoriteUseCase {
    fun getAllFavoriteMovie(): Single<List<MovieItem>>
}

class MuviFavoriteUseCaseImpl @Inject constructor(
    private val repository: MuviFavoriteRepository
) : MuviFavoriteUseCase {

    private fun mapToMovieModel(data: List<MuviFavorites>): List<MovieItem> {
        val list: MutableList<MovieItem> = mutableListOf()
        data.forEach { data ->
            val item = MovieItem(
                id = data.movieFavoriteId,
                movieId = data.movieFavoriteId,
                posterPath = data.posterPath,
                overview = data.overview,
                title = data.movieTitle,
                backdropPath = data.backdropPath,
                releaseDate = data.releaseDate
            )
            list.add(item)
        }
        return list
    }

    override fun getAllFavoriteMovie(): Single<List<MovieItem>> {
        return repository.getAllFavoriteMovie()
            .map(::mapToMovieModel)
            .compose(singleIo())
    }
}