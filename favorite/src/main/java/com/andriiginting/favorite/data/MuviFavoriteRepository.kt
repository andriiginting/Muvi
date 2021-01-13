package com.andriiginting.favorite.data

import com.andriiginting.common_database.MuviDatabase
import com.andriiginting.common_database.MuviFavoriteDAO
import com.andriiginting.common_database.MuviFavorites
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

interface MuviFavoriteRepository {
    fun getAllFavoriteMovie(): Flowable<List<MuviFavorites>>
}

class MuviFavoriteRepositoryImpl @Inject constructor(
    private val muviFavoriteDAO: MuviDatabase
) : MuviFavoriteRepository {
    override fun getAllFavoriteMovie(): Flowable<List<MuviFavorites>> {
        return muviFavoriteDAO.theaterDAO().getAllFavoriteMovie()
    }
}