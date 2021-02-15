package com.andriiginting.search.domain

import com.andriiginting.core_network.MovieResponse
import com.andriiginting.search.data.MuviSearchRepository
import com.andriiginting.uttils.singleIo
import io.reactivex.Single
import javax.inject.Inject

interface MuviSearchUseCase {
    fun getMovieFrom(query: String): Single<MovieResponse>
}

class MuviSearchUseCaseImpl @Inject constructor(
    private val repository: MuviSearchRepository
) : MuviSearchUseCase {

    override fun getMovieFrom(query: String): Single<MovieResponse> {
        return repository.searchMovie(query)
            .compose(singleIo())
    }
}