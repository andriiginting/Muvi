package com.andriiginting.core_network

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface MuviSearchService {
    @GET("search/movie")
    fun searchMovies(@Query("query") query: String): Single<MovieResponse>
}