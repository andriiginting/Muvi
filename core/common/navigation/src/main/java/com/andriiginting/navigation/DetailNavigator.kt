package com.andriiginting.navigation

import android.content.Intent
import android.net.Uri

private const val SCHEME = "muvi://detail/"

class DetailNavigator {
    companion object {

        @JvmStatic
        fun getDetailPageIntent(movieId: String): Intent {
            return Intent(Intent.ACTION_VIEW, Uri.parse(SCHEME + movieId))
        }
    }
}