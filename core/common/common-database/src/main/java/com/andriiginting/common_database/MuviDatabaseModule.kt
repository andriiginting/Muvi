package com.andriiginting.common_database

import android.content.Context
import androidx.room.Room
import com.andriiginting.common_database.Constants.FAVORITE_DATABASE_NAME
import dagger.Module
import dagger.Provides
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@Module
class MuviDatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(context: Context): MuviDatabase {
        val factory = SupportFactory(
            SQLiteDatabase.getBytes("FAVORITE_DATABASE_NAME".toCharArray())
        )
        return Room
            .databaseBuilder(context, MuviDatabase::class.java, FAVORITE_DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }
}
