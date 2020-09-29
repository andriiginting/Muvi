package com.andriiginting.detail

import androidx.fragment.app.FragmentActivity
import com.andriiginting.base_ui.MuviBaseActivity
import com.andriiginting.core_network.MovieItem
import com.andriiginting.detail.di.MuviDetailInjector
import com.andriiginting.detail.presentation.MuviDetailViewModel
import com.andriiginting.navigation.DetailNavigator
import com.andriiginting.uttils.loadImage
import kotlinx.android.synthetic.main.activity_muvi_detail.*

class MuviDetailActivity : MuviBaseActivity<MuviDetailViewModel>() {

    private var data: MovieItem? = null

    override fun getLayoutId(): Int = R.layout.activity_muvi_detail

    override fun setupView() {
        data = intent.getParcelableExtra(DetailNavigator.INTENT_DETAIL_MOVIE_ITEM)
        setUpDetailScreen()
    }

    override fun setData() {
        viewModel.getSimilarMovie(data?.movieId.orEmpty())
    }

    override fun setupInjector() = MuviDetailInjector.of(this)

    override fun setViewModel(): Class<MuviDetailViewModel> = MuviDetailViewModel::class.java

    override fun setObserver(): FragmentActivity = this

    private fun setUpDetailScreen() {
        tvMovieTitle.text = data?.title.orEmpty()
        tvMovieDescription.text = data?.overview

        ivBackNavigation.setOnClickListener {
            onBackPressed()
        }

        ivPosterBackdrop.loadImage(data?.backdropPath.orEmpty())
    }
}