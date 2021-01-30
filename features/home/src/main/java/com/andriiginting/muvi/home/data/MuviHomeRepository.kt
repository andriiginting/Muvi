package com.andriiginting.muvi.home.data

import com.andriiginting.core_network.MovieResponse
import com.andriiginting.core_network.MuviHomeService
import io.reactivex.Single
import javax.inject.Inject

interface MuviHomeRepository {
    fun getPopularMovie(): Single<MovieResponse>
    fun getLatestMovies(): Single<MovieResponse>
    fun getNowPlayingMovies(): Single<MovieResponse>
    fun getTopRatedMovies(): Single<MovieResponse>
    fun getUpcomingMovies(): Single<MovieResponse>
}

class MuviHomeRepositoryImpl @Inject constructor(
    private val service: MuviHomeService
) : MuviHomeRepository {

    override fun getPopularMovie(): Single<MovieResponse> = service.getPopularMovies()

    override fun getLatestMovies(): Single<MovieResponse> = service.getLatestMovies()

    override fun getNowPlayingMovies(): Single<MovieResponse> = service.getNowPlayingMovies()

    override fun getTopRatedMovies(): Single<MovieResponse> = service.getTopRatedMovies()

    override fun getUpcomingMovies(): Single<MovieResponse> = service.getUpcomingMovies()
}