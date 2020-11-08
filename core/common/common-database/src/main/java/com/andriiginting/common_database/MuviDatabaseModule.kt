package com.andriiginting.common_database

import android.content.Context
import androidx.room.Room
import com.andriiginting.common_database.Constants.FAVORITE_DATABASE_NAME
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MuviDatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(context: Context): MuviDatabase {
        return Room
            .databaseBuilder(context, MuviDatabase::class.java, FAVORITE_DATABASE_NAME)
            .build()
    }

    @Provides
    @Singleton
    fun provideDatabaseDAO(muviDatabase: MuviDatabase): MuviFavoriteDAO {
        return muviDatabase.theaterDAO()
    }
}
