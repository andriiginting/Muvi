package com.andriiginting.muvi.detail.data

import com.andriiginting.common_database.MuviFavoriteDAO
import com.andriiginting.common_database.MuviFavorites
import com.andriiginting.core_network.MovieItem
import com.andriiginting.core_network.MovieResponse
import com.andriiginting.core_network.MuviDetailService
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import javax.inject.Inject

interface MuviDetailRepository {
    fun getDetailMovie(movieId: String): Single<MovieItem>
    fun getSimilarMovie(movieId: String): Single<MovieResponse>
    fun storeToDatabase(data: MuviFavorites): Completable
    fun isFavoriteMovie(movieId: Int): Maybe<MuviFavorites>
    fun removeFromDatabase(movieId: String): Completable
}

class MuviDetailRepositoryImpl @Inject constructor(
    private val service: MuviDetailService,
    private val database: MuviFavoriteDAO
) : MuviDetailRepository {
    override fun getDetailMovie(movieId: String): Single<MovieItem> {
        return service.getDetailMovies(movieId)
    }

    override fun getSimilarMovie(movieId: String): Single<MovieResponse> {
        return service.getSimilarMovie(movieId)
    }

    override fun storeToDatabase(data: MuviFavorites): Completable {
        return database.insertFavoriteMovie(data)
    }

    override fun isFavoriteMovie(movieId: Int): Maybe<MuviFavorites> {
        return database.isFavorite(movieId)
    }

    override fun removeFromDatabase(movieId: String): Completable {
        return database.deleteMovie(movieId)
    }
}