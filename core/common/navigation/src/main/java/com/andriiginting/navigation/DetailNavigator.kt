package com.andriiginting.navigation

import android.content.Context
import android.content.Intent
import android.net.Uri

private const val MUVI_DETAIL_IMPLICIT_INTENT = "muvi://detail/"

class DetailNavigator {
    companion object {
        const val INTENT_DETAIL_MOVIE_ITEM = "intent_movie_item"

        @JvmStatic
        fun getDetailPageIntent(movieId: String): Intent {
            return Intent(
                Intent.ACTION_VIEW,
                Uri.parse(MUVI_DETAIL_IMPLICIT_INTENT + movieId)
            )
        }
    }
}