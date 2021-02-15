package com.andriiginting.search

import androidx.lifecycle.Observer
import com.andriiginting.search.domain.MuviSearchUseCase
import com.andriiginting.search.ui.MuviSearchViewModel
import com.andriiginting.search.ui.MuviSearchViewState
import com.andriiginting.uttils.testhelper.InstantRuleExecution
import com.andriiginting.uttils.testhelper.TrampolineSchedulerRX
import com.andriiginting.uttils.testhelper.getDummyResponse
import com.andriiginting.uttils.testhelper.getEmptyMovieResponse
import com.nhaarman.mockito_kotlin.*
import io.reactivex.Single
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations

class MuviSearchViewModelTest {
    private val useCase: MuviSearchUseCase = mock()
    private lateinit var viewModel: MuviSearchViewModel
    private val observer = mock<Observer<MuviSearchViewState>>()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = MuviSearchViewModel(useCase)

        TrampolineSchedulerRX.start()
        InstantRuleExecution.start()
        viewModel.state.observeForever(observer)
    }

    @Test
    fun `when init search view should show empty screen`() {
        viewModel.initialView()

        verify(observer).onChanged(MuviSearchViewState.EmptyScreen)
        verifyNoMoreInteractions(observer)
        clearInvocations(observer)
    }

    @Test
    fun `when search view cleared should show empty screen and clear text`() {
        viewModel.onSearchTextCleared()

        verify(observer).onChanged(MuviSearchViewState.EmptyScreen)
        verify(observer).onChanged(MuviSearchViewState.ClearText)
        verifyNoMoreInteractions(observer)
        clearInvocations(observer)
    }

    @Test
    fun `when submitted movie title should return success`() {
        val query = "title"

        whenever(useCase.getMovieFrom(query))
            .thenReturn(Single.just(getDummyResponse()))

        viewModel.submittedQuery(query)

        verify(useCase, atLeastOnce()).getMovieFrom(query)
        verify(observer, atLeastOnce()).onChanged(MuviSearchViewState.HideKeyboard)
        verify(observer, atLeastOnce()).onChanged(MuviSearchViewState.ShowLoading)
        verify(observer, atLeastOnce()).onChanged(MuviSearchViewState.HideLoading)

        verifyNoMoreInteractions(useCase, observer)
        clearInvocations(useCase, observer)
    }

    @Test
    fun `when submitted movie title should return success with empty data`() {
        val query = "title"

        whenever(useCase.getMovieFrom(query))
            .thenReturn(Single.just(getEmptyMovieResponse()))

        viewModel.submittedQuery(query)

        verify(useCase, atLeastOnce()).getMovieFrom(query)
        verify(observer, atLeastOnce()).onChanged(MuviSearchViewState.HideKeyboard)
        verify(observer, atLeastOnce()).onChanged(MuviSearchViewState.ShowLoading)
        verify(observer, atLeastOnce()).onChanged(MuviSearchViewState.HideLoading)

        verifyNoMoreInteractions(useCase, observer)
        clearInvocations(useCase, observer)
    }

    @After
    fun tearDown() {
        TrampolineSchedulerRX.tearDown()
        InstantRuleExecution.tearDown()
    }
}