package com.andriiginting.muvi.home.ui

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import com.andriiginting.base_ui.MuviBaseActivity
import com.andriiginting.base_ui.MuviBaseAdapter
import com.andriiginting.core_network.MovieItem
import com.andriiginting.muvi.home.R
import com.andriiginting.muvi.home.di.MuviHomeInjector
import com.andriiginting.navigation.DetailNavigator
import com.andriiginting.uttils.loadAnimation
import com.andriiginting.uttils.makeGone
import com.andriiginting.uttils.makeVisible
import com.andriiginting.uttils.setGridView
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.item_loading_component.*

private const val HOME_COLUMN_SIZE = 2

class HomeActivity : MuviBaseActivity<MuviHomeViewModel>() {

    private lateinit var homeAdapter: MuviBaseAdapter<MovieItem, HomeViewHolder>

    override fun getLayoutId(): Int = R.layout.activity_home

    override fun setupView() {
        setUpAdapter()
        setUpHome()
        setupObserver()
    }

    override fun setData() = viewModel.getMovieData()

    override fun setupInjector() = MuviHomeInjector.of(this)

    override fun setViewModel(): Class<MuviHomeViewModel> = MuviHomeViewModel::class.java

    override fun setObserver(): FragmentActivity = this

    private fun setUpHome() {
        rvMovies.apply {
            setGridView(HOME_COLUMN_SIZE)
            adapter = homeAdapter
        }
    }

    private fun setUpAdapter() {
        homeAdapter = MuviBaseAdapter({ parent, _ ->
            HomeViewHolder.inflate(parent)
        }, { viewHolder, _, item ->
            viewHolder.bind(item.posterPath)
            viewHolder.setPosterAction {
                DetailNavigator
                    .getDetailPageIntent(item.id)
                    .also(::startActivity)
            }
        })
    }

    private fun setupObserver() {
        viewModel.state.observe(this, Observer { state ->
            when (state) {
                is HomeViewState.ShowLoading -> {
                    ivLoadingIndicator.apply {
                        makeVisible()
                        loadAnimation("loading.json")
                    }
                }
                is HomeViewState.HideLoading -> {
                    ivLoadingIndicator.makeGone()
                }
                is HomeViewState.GetMovieDataError -> {
                    layoutError.showErrorScreen()
                }
                is HomeViewState.GetMovieData -> {
                    homeAdapter.safeAddAll(state.data.resultsIntent)
                    layoutError.hideErrorScreen()
                }
            }
        })
    }
}