package com.andriiginting.favorite

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.andriiginting.common_database.MuviDatabase
import com.andriiginting.common_database.MuviFavoriteDAO
import com.andriiginting.common_database.MuviFavorites
import com.andriiginting.favorite.data.MuviFavoriteRepository
import com.andriiginting.favorite.data.MuviFavoriteRepositoryImpl
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MuviFavoriteRepositoryTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: MuviDatabase
    private lateinit var dao: MuviFavoriteDAO

    private lateinit var repository: MuviFavoriteRepository

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, MuviDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        dao = database.theaterDAO()

        repository = MuviFavoriteRepositoryImpl(database)
    }

    @Test
    fun `get favorite muvi from database when no saved muvi`() {
        repository.getAllFavoriteMovie()
        dao.getAllFavoriteMovie()
            .test()
            .assertValue { it.isEmpty() }
    }

    @Test
    fun `get all favorite muvi from database when inserted muvi`() {
        dao.insertFavoriteMovie(getFavoritesDummy())

        repository.getAllFavoriteMovie()
        dao.getAllFavoriteMovie()
            .test()
            .assertValue { it.first().movieTitle == getFavoritesDummy().movieTitle }
    }

    @After
    fun tearDown() {
        database.close()
    }

    private fun getFavoritesDummy() = MuviFavorites(
        movieFavoriteId = "324668",
        movieTitle = "Jason Bourne",
        posterPath = "/lFSSLTlFozwpaGlO31OoUeirBgQ.jpg",
        overview = "The most dangerous former operative of the CIA is drawn out of hiding to uncover hidden truths about his past.",
        backdropPath = "/AoT2YrJUJlg5vKE3iMOLvHlTd3m.jpg",
        releaseDate ="2016-07-27"
    )
}