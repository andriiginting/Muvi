package com.andriiginting.navigation

import android.content.Intent
import android.net.Uri

private const val SCHEME = "muvi://"
private const val FAVORITE = "favorite"

class FavoriteNavigator {
    companion object {
        @JvmStatic
        fun getFavoritePageIntent(): Intent {
            return Intent(Intent.ACTION_VIEW, Uri.parse(SCHEME + FAVORITE))
        }
    }
}