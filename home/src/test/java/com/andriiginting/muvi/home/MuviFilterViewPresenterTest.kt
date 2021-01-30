package com.andriiginting.muvi.home

import com.andriiginting.muvi.home.data.MuviFilterContract
import com.andriiginting.muvi.home.ui.HomeFilterData
import com.andriiginting.muvi.home.ui.filter.HomeFilterViewContract
import com.andriiginting.muvi.home.ui.filter.HomeFilterViewPresenter
import com.andriiginting.muvi.home.ui.filter.HomeFilterViewPresenterImpl
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.junit.Before
import org.junit.Test

class MuviFilterViewPresenterTest {
    private val view: HomeFilterViewContract = mock()

    lateinit var presenter: HomeFilterViewPresenter

    @Before
    fun setup() {
        presenter = HomeFilterViewPresenterImpl(view)
    }

    @Test
    fun `should initial filter view when view attached`() {
        presenter.initFilterView()

        verify(view).showLoading()
        verify(view).setupFilterView()
    }

    @Test
    fun `should set filter data when view attached`() {
        val data = getFilters()

        presenter.setFilter(data, 0)

        verify(view).hideLoading()
        verify(view).setFilterData(data)
    }

    private fun getFilters(): MutableList<HomeFilterData> {
        return mutableListOf(
            HomeFilterData(MuviFilterContract.ALL, true),
            HomeFilterData(MuviFilterContract.LATEST),
            HomeFilterData(MuviFilterContract.NOW_PLAYING),
            HomeFilterData(MuviFilterContract.TOP_RATED),
            HomeFilterData(MuviFilterContract.UPCOMING)
        )
    }
}