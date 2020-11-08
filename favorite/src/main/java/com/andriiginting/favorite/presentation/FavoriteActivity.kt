package com.andriiginting.favorite.presentation

import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import com.airbnb.deeplinkdispatch.DeepLink
import com.andriiginting.base_ui.MuviBaseActivity
import com.andriiginting.base_ui.MuviBaseAdapter
import com.andriiginting.core_network.MovieItem
import com.andriiginting.favorite.R
import com.andriiginting.favorite.di.MuviFavoriteInjector
import com.andriiginting.uttils.makeGone
import com.andriiginting.uttils.makeVisible
import com.andriiginting.uttils.setGridView
import kotlinx.android.synthetic.main.activity_favorite.*

@DeepLink("muvi://favorite")
class FavoriteActivity : MuviBaseActivity<MuviFavoriteViewModel>() {

    private val favoriteAdapter: MuviBaseAdapter<MovieItem, MuviFavoriteViewHolder> by lazy {
        MuviBaseAdapter<MovieItem, MuviFavoriteViewHolder>({ parent, _ ->
            MuviFavoriteViewHolder.inflate(parent)
        }, { viewHolder, _, item ->
            viewHolder.bind(item.posterPath)
        })
    }

    override fun getLayoutId(): Int = R.layout.activity_favorite

    override fun setupView() {
        setUpRecyclerView()
        setUpObserver()

        fabBackNavigation.setOnClickListener {
            onBackPressed()
        }
    }

    override fun setData() {
        viewModel.getMovies()
    }

    override fun setupInjector() {
        MuviFavoriteInjector.of(this)
    }

    override fun setViewModel(): Class<MuviFavoriteViewModel> = MuviFavoriteViewModel::class.java

    override fun setObserver(): FragmentActivity = this

    private fun setUpRecyclerView() {
        rvFavorite.apply {
            setGridView()
            adapter = favoriteAdapter
        }
    }

    private fun setUpObserver() {
        viewModel.state.observe(this, Observer { state ->
            when (state) {
                is FavoriteViewState.ShowLoading -> {
                    pbLoadingIndicator.makeVisible()
                }
                is FavoriteViewState.HideLoading -> {
                    pbLoadingIndicator.makeGone()
                }
                is FavoriteViewState.GetFavoriteMovie -> {
                    Log.d("favorite", state.data.toString())
                    favoriteAdapter.safeAddAll(state.data)
                    rvFavorite.makeVisible()
                }
                is FavoriteViewState.ShowError -> {
                    pbLoadingIndicator.makeGone()
                    layoutError.makeVisible()
                    layoutError.showErrorScreen()
                    emptyScreen.makeGone()
                    rvFavorite.makeGone()
                }

                is FavoriteViewState.ShowEmptyState -> {
                    pbLoadingIndicator.makeGone()
                    layoutError.makeGone()
                    emptyScreen.makeVisible()
                    emptyScreen.showEmptyScreen()
                    rvFavorite.makeGone()
                }
            }
        })
    }
}