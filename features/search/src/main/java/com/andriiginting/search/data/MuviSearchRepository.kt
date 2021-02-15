package com.andriiginting.search.data

import com.andriiginting.core_network.MovieResponse
import com.andriiginting.core_network.MuviSearchService
import io.reactivex.Single
import javax.inject.Inject

interface MuviSearchRepository {
    fun searchMovie(query: String): Single<MovieResponse>
}

class MuviSearchRepositoryImpl @Inject constructor(
    private val service: MuviSearchService
) : MuviSearchRepository {

    override fun searchMovie(query: String): Single<MovieResponse> {
        return service.searchMovies(query)
    }
}