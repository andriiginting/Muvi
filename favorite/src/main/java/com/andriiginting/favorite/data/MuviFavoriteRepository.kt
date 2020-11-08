package com.andriiginting.favorite.data

import com.andriiginting.common_database.MuviFavoriteDAO
import com.andriiginting.common_database.MuviFavorites
import io.reactivex.Single
import javax.inject.Inject

interface MuviFavoriteRepository {
    fun getAllFavoriteMovie(): Single<List<MuviFavorites>>
}

class MuviFavoriteRepositoryImpl @Inject constructor(
    private val muviFavoriteDAO: MuviFavoriteDAO
) : MuviFavoriteRepository {
    override fun getAllFavoriteMovie(): Single<List<MuviFavorites>> {
        return muviFavoriteDAO.getAllFavoriteMovie()
    }
}