package com.andriiginting.navigation

import android.content.Intent
import android.net.Uri

private const val SCHEME = "muvi://"
private const val SEARCH = "search"

class SearchNavigator {
    companion object {
        @JvmStatic
        fun getSearchPageIntent(): Intent {
            return Intent(Intent.ACTION_VIEW, Uri.parse(SCHEME + SEARCH))
        }
    }
}