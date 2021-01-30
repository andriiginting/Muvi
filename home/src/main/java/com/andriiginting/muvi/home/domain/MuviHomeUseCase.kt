package com.andriiginting.muvi.home.domain

import com.andriiginting.core_network.MovieResponse
import com.andriiginting.muvi.home.data.MuviHomeRepository
import com.andriiginting.uttils.singleIo
import io.reactivex.Single
import javax.inject.Inject

interface MuviHomeUseCase {
    fun getAllMovies(): Single<MovieResponse>
    fun getLatestMovies(): Single<MovieResponse>
    fun getNowPlayingMovies(): Single<MovieResponse>
    fun getTopRatedMovies(): Single<MovieResponse>
    fun getUpcomingMovies(): Single<MovieResponse>
}

class MuviHomeUseCaseImpl @Inject constructor(
    private val repository: MuviHomeRepository
) : MuviHomeUseCase {

    override fun getAllMovies(): Single<MovieResponse> {
        return repository.getPopularMovie()
            .compose(singleIo())
    }

    override fun getLatestMovies(): Single<MovieResponse> {
        return repository.getLatestMovies()
            .compose(singleIo())
    }

    override fun getNowPlayingMovies(): Single<MovieResponse> {
        return repository.getNowPlayingMovies()
            .compose(singleIo())
    }

    override fun getTopRatedMovies(): Single<MovieResponse> {
        return repository.getTopRatedMovies()
            .compose(singleIo())
    }

    override fun getUpcomingMovies(): Single<MovieResponse> {
        return repository.getUpcomingMovies()
            .compose(singleIo())
    }
}