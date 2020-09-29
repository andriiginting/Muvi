package com.andriiginting.navigation

import android.content.Context
import android.content.Intent
import com.andriiginting.core_network.MovieItem

private const val MUVI_DETAIL_IMPLICIT_INTENT = "MUVI_DETAIL_IMPLICIT_INTENT"

class DetailNavigator {
    companion object {
        const val INTENT_DETAIL_MOVIE_ITEM = "intent_movie_item"

        @JvmStatic
        fun getDetailPageIntent(context: Context?, movieItem: MovieItem): Intent {
            return Intent().apply {
                action = MUVI_DETAIL_IMPLICIT_INTENT
                context?.let { `package` = it.packageName }
                putExtra(INTENT_DETAIL_MOVIE_ITEM, movieItem)
            }
        }
    }
}