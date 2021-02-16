package com.andriiginting.muvi.home.ui.searchbar

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.andriiginting.muvi.home.R
import com.andriiginting.navigation.SearchNavigator

class HomeSearchBarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    init {
        View.inflate(context, R.layout.item_home_search_bar_component, this)
        setupView()
    }

    private fun setupView() {
        setOnClickListener {
            SearchNavigator
                .getSearchPageIntent()
                .also(context::startActivity)
        }
    }

}