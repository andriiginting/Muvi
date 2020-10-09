package com.andriiginting.muvi.detail

import com.andriiginting.common_database.MuviFavoriteDAO
import com.andriiginting.core_network.MuviDetailService
import com.andriiginting.muvi.detail.data.MuviDetailRepository
import com.andriiginting.muvi.detail.data.MuviDetailRepositoryImpl
import com.nhaarman.mockito_kotlin.atLeastOnce
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import org.junit.Before
import org.junit.Test

class MuviDetailRepositoryTest {
    private val service: MuviDetailService = mock()
    private val database: MuviFavoriteDAO = mock()
    private lateinit var repository: MuviDetailRepository

    @Before
    fun setUp() {
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
        val id = 1
        whenever(database.isFavorite(id))
            .thenReturn(Maybe.just(getFavoritesDummy()))

        val test = database.isFavorite(id).test()
        repository.isFavoriteMovie(id)

        test.apply {
            assertComplete()
            assertNoErrors()
            assertValue {
                it == getFavoritesDummy()
            }
        }

        verify(database, atLeastOnce()).isFavorite(id)
    }

    @Test
    fun `when store popular movie to db return success`() {

        whenever(database.insertFavoriteMovie(getFavoritesDummy()))
            .thenReturn(Completable.complete())

        val test = database.insertFavoriteMovie(getFavoritesDummy()).test()
        repository.storeToDatabase(getFavoritesDummy())

        test.apply {
            assertComplete()
            assertNoErrors()
        }

        verify(database, atLeastOnce()).insertFavoriteMovie(getFavoritesDummy())
    }

    @Test
    fun `when store popular movie to db return error`() {
        val error = Throwable("unable to save to db")

        whenever(database.insertFavoriteMovie(getFavoritesDummy()))
            .thenReturn(Completable.error(error))

        val test = database.insertFavoriteMovie(getFavoritesDummy()).test()
        repository.storeToDatabase(getFavoritesDummy())

        test.apply {
            assertNotComplete()
            assertError(error)
        }

        verify(database, atLeastOnce()).insertFavoriteMovie(getFavoritesDummy())
    }

    @Test
    fun `when remove popular movie to db return success`() {
        val id = "1"
        whenever(database.deleteMovie(id))
            .thenReturn(Completable.complete())

        val test = database.deleteMovie(id).test()
        repository.removeFromDatabase(id)

        test.apply {
            assertComplete()
            assertNoErrors()
        }

        verify(database, atLeastOnce()).deleteMovie(id)
    }

    @Test
    fun `when remove popular movie to db return error`() {
        val error = Throwable("unable to remove from db")
        val id = "1"
        whenever(database.deleteMovie(id))
            .thenReturn(Completable.error(error))

        val test = database.deleteMovie(id).test()
        repository.removeFromDatabase(id)

        test.apply {
            assertNotComplete()
            assertError(error)
        }

        verify(database, atLeastOnce()).deleteMovie(id)
    }
}