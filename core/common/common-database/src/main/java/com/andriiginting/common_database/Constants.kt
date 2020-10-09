package com.andriiginting.common_database

object Constants {
    const val TABLE_NAME = "muvi_db"
    const val GET_ALL_FAVORITE_MOVIE = "SELECT * FROM $TABLE_NAME"
    const val FILTER_FAVORITE_MOVIE_WITH_ID = "SELECT * FROM $TABLE_NAME WHERE movie_id = :movieId LIMIT 1"
    const val DELETE_FAVORITE_MOVIE_WITH_ID = "DELETE FROM $TABLE_NAME WHERE movie_id = :movieId "
    const val FAVORITE_DATABASE_NAME = "muvi_favorites.db"
}