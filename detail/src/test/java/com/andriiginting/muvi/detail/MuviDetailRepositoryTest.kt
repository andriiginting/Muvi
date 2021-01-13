package com.andriiginting.muvi.detail

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.andriiginting.common_database.MuviDatabase
import com.andriiginting.common_database.MuviFavoriteDAO
import com.andriiginting.core_network.MuviDetailService
import com.andriiginting.muvi.detail.data.MuviDetailRepository
import com.andriiginting.muvi.detail.data.MuviDetailRepositoryImpl
import com.nhaarman.mockito_kotlin.atLeastOnce
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MuviDetailRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val service: MuviDetailService = mock()
    private lateinit var database: MuviDatabase
    private lateinit var dao: MuviFavoriteDAO
    private lateinit var repository: MuviDetailRepository

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, MuviDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        dao = database.theaterDAO()

        repository =
            MuviDetailRepositoryImpl(service, database)
    }

    @Test
    fun `when get popular movie, should return list of movie from service`() {
        val id = "id"

        whenever(service.getSimilarMovie(id))
            .thenReturn(Single.just(getDummyResponse()))

        val test = service.getSimilarMovie(id).test()
        repository.getSimilarMovie(id)

        test.apply {
            assertComplete()
            assertNoErrors()
        }

        verify(service, atLeastOnce()).getSimilarMovie(id)
    }

    @Test
    fun `when check movie popular movie store in db will return success`() {
        val id = 0
        dao.insertFavoriteMovie(getFavoritesDummy())

        val test = dao.isFavorite(id).test()
        repository.isFavoriteMovie(id)

        test.apply {
            assertComplete()
            assertNoErrors()
        }
    }

    @Test
    fun `when store popular movie to db return success`() {
        dao.insertFavoriteMovie(getFavoritesDummy())

        repository.storeToDatabase(getFavoritesDummy())

        dao.getAllFavoriteMovie()
            .test()
            .assertValue {
                it.first().movieTitle == getFavoritesDummy().movieTitle
            }
    }

    @Test
    fun `when remove popular movie to db return success`() {
        val id = "1"
        dao.insertFavoriteMovie(getFavoritesDummy())

        val test = dao.deleteMovie(id).test()
        repository.removeFromDatabase(id)

        test.apply {
            assertComplete()
            assertNoErrors()
        }
    }

    @After
    fun tearDown() {
        database.close()
    }
}