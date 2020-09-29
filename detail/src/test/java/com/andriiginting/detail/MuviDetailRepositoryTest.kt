package com.andriiginting.detail

import com.andriiginting.core_network.MuviDetailService
import com.andriiginting.detail.data.MuviDetailRepository
import com.andriiginting.detail.data.MuviDetailRepositoryImpl
import com.nhaarman.mockito_kotlin.atLeastOnce
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import org.junit.Before
import org.junit.Test

class MuviDetailRepositoryTest {
    private val service: MuviDetailService = mock()
    private lateinit var repository: MuviDetailRepository

    @Before
    fun setUp() {
        repository =
            MuviDetailRepositoryImpl(service)
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
}