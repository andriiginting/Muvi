package com.andriiginting.muvi.detail.domain

import com.andriiginting.common_database.MuviFavorites
import com.andriiginting.core_network.MovieItem
import javax.inject.Inject

interface MuviDetailMapper {
    fun mapToMuviFavorite(data: MovieItem): MuviFavorites
    fun mapToMovieItem(data: MuviFavorites): MovieItem
}

class MuviDetailMapperImpl @Inject constructor() : MuviDetailMapper {
    override fun mapToMuviFavorite(data: MovieItem): MuviFavorites {
        return MuviFavorites(
            movieFavoriteId = data.movieId.takeIf { it.isNotEmpty() } ?: data.id,
            movieTitle = data.title,
            posterPath = data.posterPath,
            overview = data.overview,
            backdropPath = data.backdropPath,
            releaseDate = data.releaseDate
        )
    }

    override fun mapToMovieItem(data: MuviFavorites): MovieItem {
        return MovieItem(
            id = data.movieFavoriteId,
            movieId = data.movieFavoriteId,
            posterPath = data.posterPath,
            overview = data.overview,
            title = data.movieTitle,
            backdropPath = data.backdropPath,
            releaseDate = data.releaseDate
        )
    }

}