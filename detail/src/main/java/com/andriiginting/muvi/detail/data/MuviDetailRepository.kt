package com.andriiginting.muvi.detail.data

import com.andriiginting.core_network.MovieItem
import com.andriiginting.core_network.MovieResponse
import com.andriiginting.core_network.MuviDetailService
import io.reactivex.Single
import javax.inject.Inject

interface MuviDetailRepository {
    fun getDetailMovie(movieId: String): Single<MovieItem>
    fun getSimilarMovie(movieId: String): Single<MovieResponse>
}

class MuviDetailRepositoryImpl @Inject constructor(
    private val service: MuviDetailService
) : MuviDetailRepository {
    override fun getDetailMovie(movieId: String): Single<MovieItem> {
        return service.getDetailMovies(movieId)
    }

    override fun getSimilarMovie(movieId: String): Single<MovieResponse> {
        return service.getSimilarMovie(movieId)
    }
}