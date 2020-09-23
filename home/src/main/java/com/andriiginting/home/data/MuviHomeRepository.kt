package com.andriiginting.home.data

import com.andriiginting.core_network.MovieResponse
import com.andriiginting.core_network.MuviHomeService
import io.reactivex.Single
import javax.inject.Inject

interface MuviHomeRepository {
    fun getPopularMovie(): Single<MovieResponse>
}

class MuviHomeRepositoryImpl @Inject constructor(
    private val service: MuviHomeService
) : MuviHomeRepository {

    override fun getPopularMovie(): Single<MovieResponse> = service.getPopularMovies()
}