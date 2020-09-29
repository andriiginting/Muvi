package com.andriiginting.detail.data

import com.andriiginting.core_network.MovieResponse
import com.andriiginting.core_network.MuviDetailService
import io.reactivex.Single
import javax.inject.Inject

interface MuviDetailRepository {
    fun getSimilarMovie(movieId: String): Single<MovieResponse>
}

class MuviDetailRepositoryImpl @Inject constructor(
    private val service: MuviDetailService
) : MuviDetailRepository {
    override fun getSimilarMovie(movieId: String): Single<MovieResponse> {
        return service.getSimilarMovie(movieId)
    }
}