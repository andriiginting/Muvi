package com.andriiginting.muvi.home.ui

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.andriiginting.base_ui.MuviBaseActivity
import com.andriiginting.base_ui.MuviBaseAdapter
import com.andriiginting.core_network.MovieItem
import com.andriiginting.muvi.home.R
import com.andriiginting.muvi.home.di.MuviHomeInjector
import com.andriiginting.navigation.DetailNavigator
import com.andriiginting.navigation.FavoriteNavigator
import com.andriiginting.uttils.loadImage
import com.andriiginting.uttils.makeGone
import com.andriiginting.uttils.makeVisible
import com.andriiginting.uttils.setGridView
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.item_home_banner_component.*

private const val HOME_COLUMN_SIZE = 2

class HomeActivity : MuviBaseActivity<MuviHomeViewModel>() {

    private lateinit var homeAdapter: MuviBaseAdapter<MovieItem, HomeViewHolder>

    override fun getLayoutId(): Int = R.layout.activity_home

    override fun setupView() {
        setUpAdapter()
        setUpHome()
        setupObserver()
        setupFilterView()
        setupFavoriteButton()
    }

    override fun setData() = viewModel.getMovieData()

    override fun setupInjector() = MuviHomeInjector.of(this)

    override fun setViewModel(): Class<MuviHomeViewModel> = MuviHomeViewModel::class.java

    override fun setObserver(): FragmentActivity = this

    override fun onResume() {
        super.onResume()
        viewModel.getHomeBanner()
    }

    private fun setUpHome() {
        rvMovies.apply {
            setGridView(HOME_COLUMN_SIZE)
            adapter = homeAdapter
        }
    }

    private fun setupFilterView() {
        filterView.setOnFilterListener { position ->
            viewModel.getFilteredData(position)
        }
    }

    private fun setupFavoriteButton() {
        fabFavoriteMovie.apply {
            setOnClickListener {
                FavoriteNavigator
                    .getFavoritePageIntent()
                    .let(::startActivity)
            }
            bringToFront()
        }

        rvMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0 || dy < 0) {
                    fabFavoriteMovie.hide()
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    fabFavoriteMovie.show()
                }
            }
        })
    }


    private fun setUpAdapter() {
        homeAdapter = MuviBaseAdapter({ parent, _ ->
            HomeViewHolder.inflate(parent)
        }, { viewHolder, _, item ->
            viewHolder.bind(item.posterPath.orEmpty())
            viewHolder.setPosterAction {
                DetailNavigator
                    .getDetailPageIntent(item.id)
                    .also(::startActivity)
            }
        })
    }

    private fun setupObserver() {
        viewModel.bannerState.observe(this, Observer { state ->
            when (state) {
                is HomeBannerState.BannerError -> {
                    cardBannerView.makeGone()
                }
                is HomeBannerState.GetHomeBannerData -> {
                    tvMovieBannerTitle.text = state.data.movie.title
                    ivMovieBanner.apply {
                        loadImage(state.data.movie.backdropPath.orEmpty())
                        setOnClickListener {
                            DetailNavigator
                                .getDetailPageIntent(state.data.movie.id)
                                .also(::startActivity)
                        }
                    }
                }
            }
        })

        viewModel.state.observe(this, Observer { state ->
            when (state) {
                is HomeViewState.ShowLoading -> {
                    ivLoadingIndicator.apply {
                        startShimmer()
                        makeVisible()
                    }

                    fabFavoriteMovie.makeGone()
                    rvMovies.makeGone()
                    layoutEmpty.hideEmptyScreen()
                }
                is HomeViewState.HideLoading -> {
                    ivLoadingIndicator.apply {
                        stopShimmer()
                        makeGone()
                    }

                    fabFavoriteMovie.makeVisible()
                    rvMovies.makeVisible()
                    cardBannerView.makeVisible()
                }
                is HomeViewState.GetMovieDataError -> {
                    layoutError.showErrorScreen()
                }
                is HomeViewState.GetMovieData -> {
                    homeAdapter.safeAddAll(state.data.resultsIntent)
                    layoutError.hideErrorScreen()
                    layoutEmpty.hideEmptyScreen()
                }
                is HomeViewState.EmptyScreen -> {
                    homeAdapter.clear()
                    rvMovies.makeGone()
                    layoutEmpty.showEmptyScreen()
                }
            }
        })
    }
}