package com.andriiginting.muvi.home.ui.filter

import com.andriiginting.muvi.home.ui.HomeFilterData

interface HomeFilterViewPresenter {
    fun initFilterView()
    fun setFilter(filter: List<HomeFilterData>, initialPosition: Int)
    fun onFilterSelected(position: Int)
}

class HomeFilterViewPresenterImpl(
    private val view: HomeFilterViewContract
): HomeFilterViewPresenter {

    private var lastSelectedPosition = 0

    private var currentFilters: List<HomeFilterData> = emptyList()

    override fun initFilterView() {
        view.setupFilterView()
        view.showLoading()
    }

    override fun setFilter(filter: List<HomeFilterData>, initialPosition: Int) {
        val list = filter.map { homeFilterData -> HomeFilterData(homeFilterData.filter, false)  }

        list.getOrNull(initialPosition)?.isSelected = true
        lastSelectedPosition = initialPosition
        currentFilters = list

        view.hideLoading()
        view.setFilterData(list)
    }

    override fun onFilterSelected(position: Int) {
        if (lastSelectedPosition == position || position == -1) {
            return
        }

        currentFilters[lastSelectedPosition].isSelected = false
        currentFilters[position].isSelected = true

        lastSelectedPosition = position

        view.setFilterData(currentFilters)
        view.onFilterChanged(position)
        view.scrollToSelectedPosition(position)
    }
}