package com.andriiginting.muvi.detail

import com.andriiginting.muvi.detail.data.MuviDetailRepository
import com.andriiginting.muvi.detail.domain.MuviDetailUseCase
import com.andriiginting.muvi.detail.domain.MuviDetailUseCaseImpl
import com.andriiginting.uttils.testhelper.TrampolineSchedulerRX
import com.nhaarman.mockito_kotlin.atLeastOnce
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import org.junit.After
import org.junit.Before
import org.junit.Test

class MuviDetailUseCaseTest {
    private val repository: MuviDetailRepository = mock()
    private lateinit var useCase: MuviDetailUseCase

    @Before
    fun setUp() {
        useCase = MuviDetailUseCaseImpl(repository)
        TrampolineSchedulerRX.start()
    }

    @Test
    fun `should return similar movie response when call detail repository`() {
        val id = "123"
        whenever(repository.getSimilarMovie(id))
            .thenReturn(Single.just(getDummyResponse()))

        useCase.getSimilarMovie(id)

        repository.getSimilarMovie(id).test().apply {
            assertComplete()
            assertNoErrors()
            assertTerminated()
        }

        verify(repository, atLeastOnce()).getSimilarMovie(id)
    }

    @After
    fun tearDown() {
        TrampolineSchedulerRX.tearDown()
    }
}