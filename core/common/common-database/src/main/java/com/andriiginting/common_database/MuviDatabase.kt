package com.andriiginting.common_database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.andriiginting.common_database.Constants.FAVORITE_DATABASE_NAME

@Database(entities = [MuviFavorites::class], version = 1, exportSchema = false)
abstract class MuviDatabase : RoomDatabase() {
    abstract fun theaterDAO(): MuviFavoriteDAO

    companion object {
        @Volatile
        private var INSTANCE: MuviDatabase? = null

        fun getInstance(context: Context): MuviDatabase? {
            if (INSTANCE == null) {
                synchronized(MuviDatabase::class) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            MuviDatabase::class.java, FAVORITE_DATABASE_NAME
                        )
                            .fallbackToDestructiveMigrationFrom(1)
                            .build()
                    }
                }
            }
            return INSTANCE
        }

        fun onDestroy() {
            INSTANCE = null
        }
    }
}