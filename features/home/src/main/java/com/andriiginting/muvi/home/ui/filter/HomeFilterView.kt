package com.andriiginting.muvi.home.ui.filter

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.andriiginting.base_ui.MuviBaseAdapter
import com.andriiginting.muvi.home.R
import com.andriiginting.muvi.home.data.MuviFilterContract
import com.andriiginting.muvi.home.ui.HomeFilterData
import com.andriiginting.muvi.home.ui.HomeFilterViewHolder
import com.andriiginting.uttils.makeGone
import com.andriiginting.uttils.makeVisible
import com.andriiginting.uttils.setHorizontal
import kotlinx.android.synthetic.main.item_home_filter_loading_component.view.*
import kotlinx.android.synthetic.main.item_muvi_home_filter_view.view.*

interface HomeFilterViewContract {
    fun showLoading()
    fun hideLoading()
    fun setFilterData(list: List<HomeFilterData>)
    fun setupFilterView()
    fun onFilterChanged(position: Int)
    fun scrollToSelectedPosition(position: Int)
}

private const val INITIAL_FILTER_POSITION = 0
class HomeFilterView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) :HomeFilterViewContract,  FrameLayout(context, attrs) {

    private var filterOnChangedListener: ((position: Int) -> Unit)? = null
    private lateinit var filterAdapter: MuviBaseAdapter<HomeFilterData, HomeFilterViewHolder>
    private val presenter by lazy { HomeFilterViewPresenterImpl(this) }

    private val view: View =
        LayoutInflater.from(context).inflate(R.layout.item_muvi_home_filter_view, this, true)

    init {
        initView()
    }

    private fun initView() {
        presenter.initFilterView()
        presenter.setFilter(getFilters(), INITIAL_FILTER_POSITION)
    }

    override fun setupFilterView() {
        filterAdapter = MuviBaseAdapter({ parent, _ ->
            HomeFilterViewHolder.inflate(parent)
        }, { viewHolder, _, item ->
            viewHolder.bind(item)
            viewHolder.onFilterListener(presenter::onFilterSelected)
        })

        view.rvFilterMovie.apply {
            setHorizontal(context)
            adapter = filterAdapter
        }
    }

    override fun onFilterChanged(position: Int) {
        filterOnChangedListener?.invoke(position)
    }

    override fun scrollToSelectedPosition(position: Int) {
        rvFilterMovie.smoothScrollToPosition(position)
    }

    override fun showLoading() {
        view.filterLoadingIndicator.apply {
            makeVisible()
            startShimmer()
        }
        view.tvFilterTitle.makeGone()
    }

    override fun hideLoading() {
        view.filterLoadingIndicator.apply {
            makeGone()
            stopShimmer()
        }
        view.tvFilterTitle.makeVisible()
    }

    override fun setFilterData(list: List<HomeFilterData>) {
        filterAdapter.add(list)
    }

    fun setOnFilterListener(filterOnChangedListener: ((position: Int) -> Unit)) {
        this.filterOnChangedListener = filterOnChangedListener
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