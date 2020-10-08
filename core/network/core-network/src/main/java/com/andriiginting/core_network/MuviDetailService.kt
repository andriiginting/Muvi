package com.andriiginting.core_network

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface MuviDetailService {
    @GET("movie/{movie_id}/similar")
    fun getSimilarMovie(
        @Path("movie_id") movieId: String
    ): Single<MovieResponse>

    @GET("movie/{movie_id}")
    fun getDetailMovies(@Path("movie_id") movieId: String): Single<MovieItem>
}